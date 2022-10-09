package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@Transactional
public class CartService {

    private Cart cart;
    private HttpSession session;
    private UserService userService;
    private UserAccount user;

    @Autowired
    public CartService(Cart cart, HttpSession session, UserService userService, UserAccount user) {
        this.cart = cart;
        this.session = session;
        this.userService = userService;
        this.user = this.userService.getLoggedInUser();
        System.out.println("*-*".repeat(80));
        System.out.println("*-*".repeat(80));
        System.out.println();
        if (this.user != null) {

            System.out.println(this.user);
        } else {
            System.out.println("No user");
        }
        System.out.println();
        System.out.println("*-*".repeat(80));
        System.out.println("*-*".repeat(80));
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
