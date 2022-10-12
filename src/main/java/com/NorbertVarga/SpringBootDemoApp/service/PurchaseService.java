package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.PurchaseItemData_DTO;
import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.Cart;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.ProductOrder;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.PurchaseItem;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.errorHandling.UserBalanceNotEnoughException;
import com.NorbertVarga.SpringBootDemoApp.repository.ProductOrderRepository;
import com.NorbertVarga.SpringBootDemoApp.repository.PurchaseRepository;
import com.NorbertVarga.SpringBootDemoApp.validation.SharedValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseService {

    private HttpSession session;
    private final PurchaseRepository purchaseRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final SharedValidationService validationService;

    @Autowired
    public PurchaseService(HttpSession session, PurchaseRepository purchaseRepository, ProductOrderRepository productOrderRepository, UserService userService, ProductService productService, SharedValidationService validationService) {
        this.session = session;
        this.purchaseRepository = purchaseRepository;
        this.productOrderRepository = productOrderRepository;
        this.userService = userService;
        this.productService = productService;
        this.validationService = validationService;
    }

    public PurchaseItemData_DTO makePurchase() {
        UserAccount user = userService.getLoggedInUser();
        Cart cart = (Cart) session.getAttribute("cart");
        PurchaseItemData_DTO purchaseData;
        if (user != null) {
            if (user.equals(cart.getUser())) {
                // We need to check that there is enough product in stock,
                // because it is possible that in the meantime someone has bought the given product.
                List<ProductOrder> managedOrders = manageProductOrdersByTotalQuantity(cart.mapEntriesToProductOrderEntities());

                PurchaseItem purchaseItem = new PurchaseItem(user, managedOrders);

                if (purchaseItem.getTotalPrice() <= user.getBalance()) {
                    userService.decreaseBalance(user, purchaseItem.getTotalPrice());
                    productService.decreaseTotalQuantityAfterPurchase(purchaseItem.getProductOrderList());
                    purchaseData = new PurchaseItemData_DTO(purchaseRepository.save(purchaseItem));
                    cart.clearCart();
                    session.setAttribute("cart", cart);
                } else {
                    throw new UserBalanceNotEnoughException("The User don't have enough money for the purchase");
                }
            } else {
                session.invalidate();
                throw new SessionAuthenticationException("The user in the cart is not the same the");
            }
        } else {
            throw new EntityNotFoundException("The user in the cart does not match the logged in user");
        }
        return purchaseData;
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //  **  UTILS   /////////////////////////////////////////
    private List<ProductOrder> manageProductOrdersByTotalQuantity(List<ProductOrder> originalOrders) {
        List<ProductOrder> managedOrders = new ArrayList<>();
        for (ProductOrder order : originalOrders) {
            managedOrders.add(setProductOrderQuantityDependingOnAvailableProductQuantity(order));
        }
        return managedOrders;
    }

    // NOTICE: The product entities in the Cart queried earlier,
    // so we have to query it again from the database to be sure we are working with the actual right quantity.
    private ProductOrder setProductOrderQuantityDependingOnAvailableProductQuantity(ProductOrder order) {
        ProductOrder finalOrder;
        Product product = productService.getProductById(order.getProduct().getProductId());
        if (product.getTotalQuantity() < order.getQuantity()) {
            finalOrder = new ProductOrder(product, product.getTotalQuantity());
        } else {
            finalOrder = order;
        }
        return finalOrder;
    }

}