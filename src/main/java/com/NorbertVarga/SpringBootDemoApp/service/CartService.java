package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.CartInfo_DTO;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.Cart;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@Transactional
public class CartService {

    private HttpSession session;
    private UserService userService;
    private ProductService productService;
    private Cart cart;
    private UserAccount user;

    @Autowired
    public CartService(HttpSession session, UserService userService, ProductService productService) {
        this.session = session;
        this.userService = userService;
        this.productService = productService;
    }

    public void initCart() {
        UserAccount user = userService.getLoggedInUser();
        if (user != null) {
            this.user = user;
            Cart cart = new Cart(user);
            session.setAttribute("cart", cart);
        } else {
            throw new EntityNotFoundException("There is no user logged in. Cart cannot be initialize");
        }
    }

    public void addProductsToCart(Long productId, int quantity) {

    }

    public void removeProductsFromCart(Long productId, int quantity) {

    }

    public void removeProductEntryFromCart(Long productId) {

    }

    public CartInfo_DTO getCartInfoFromSession() {
        return new CartInfo_DTO((Cart) session.getAttribute("cart"));
    }
}
