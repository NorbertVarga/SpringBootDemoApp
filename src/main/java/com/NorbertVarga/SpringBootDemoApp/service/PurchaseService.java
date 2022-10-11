package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.Cart;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.ProductOrder;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootDemoApp.repository.ProductOrderRepository;
import com.NorbertVarga.SpringBootDemoApp.repository.PurchaseRepository;
import com.NorbertVarga.SpringBootDemoApp.validation.SharedValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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


    // check and manage wanted quantity of products
    // todo check user balance!
    // todo manage user balance
    //      methods in userService needed
            // checkBalance return boolean
            // decreaseBalance
    // todo manage products total quantity
    //      methods in productService
            // decreaseTotalQuantity
    // todo save the data
    // todo clear the cart
    public boolean makePurchase() {
        boolean succes;
        UserAccount user = userService.getLoggedInUser();
        Cart cart = (Cart) session.getAttribute("cart");
        if (user != null) {
            if (user.equals(cart.getUser())) {
                List<ProductOrder> productOrders = cart.mapEntriesToProductOrderEntities();

                succes = true;
            } else {
                succes = false;
                session.removeAttribute("cart");
                session.setAttribute("cart", new Cart(user));
            }
        } else {
            throw new EntityNotFoundException("There is no User Logged in");
        }
        return succes;
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //  **  UTILS   /////////////////////////////////////////
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
