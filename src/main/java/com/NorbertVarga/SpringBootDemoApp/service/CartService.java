package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.CartInfo_DTO;
import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.Cart;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private HttpSession session;
    private UserService userService;
    private ProductService productService;
    private UserAccount user;

    @Autowired
    public CartService(UserService userService, ProductService productService, HttpSession session) {
        this.session = session;
        this.userService = userService;
        this.productService = productService;
    }

    public void initCart() {

        UserAccount user = userService.getLoggedInUser();
        Cart cart = (Cart) this.session.getAttribute("cart");
        if (user != null) {
            this.user = user;
            if (cart == null) {
                session.setAttribute("cart", new Cart(user));
            }
        } else {
            throw new EntityNotFoundException("There is no user logged in. Cart cannot be initialize");
        }
    }

    public CartInfo_DTO addProductsToCart(Long productId, int quantity) {
        Cart cart = (Cart) session.getAttribute("cart");
        Product product = productService.getProductById(productId);
        if (product != null) {
            cart.addProducts(product, quantity);
            session.setAttribute("cart", cart);
        } else {
            throw new EntityNotFoundException("There is no Product with the given Id");
        }

        return new CartInfo_DTO((Cart) session.getAttribute("cart"));
    }

    public void removeProductsFromCart(Long productId, int quantity) {

    }

    public void removeProductEntryFromCart(Long productId) {

    }

    public CartInfo_DTO getCartInfoFromSession() {
        return new CartInfo_DTO((Cart) session.getAttribute("cart"));
    }
}
