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

    private final HttpSession session;
    private final UserService userService;
    private final ProductService productService;

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
            if (cart != null) {
                if (!cart.getUser().equals(user)) {
                    session.removeAttribute("cart");
                    session.setAttribute("cart", new Cart(user));
                }
            } else {
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
        return getCartInfoFromSession();
    }

    public CartInfo_DTO removeProductsFromCart(Long productId, int quantity) {
        Cart cart = (Cart) session.getAttribute("cart");
        Product product = productService.getProductById(productId);
        if (product != null) {
            cart.removeProducts(product, quantity);
            session.setAttribute("cart", cart);
        } else {
            throw new EntityNotFoundException("There is no product with the given id");
        }
        return getCartInfoFromSession();
    }

    public CartInfo_DTO removeProductEntryFromCart(Long productId) {
        Cart cart = (Cart) session.getAttribute("cart");
        Product product = productService.getProductById(productId);
        if (product != null) {
            cart.clearEntry(product);
            session.setAttribute("cart", cart);
        } else {
            throw new EntityNotFoundException("There is no product with the given id");
        }
        return getCartInfoFromSession();
    }

    public CartInfo_DTO clearCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        cart.clearCart();
        session.setAttribute("cart", cart);
        return getCartInfoFromSession();
    }

    public CartInfo_DTO getCartInfoFromSession() {
        return new CartInfo_DTO((Cart) session.getAttribute("cart"));
    }
}
