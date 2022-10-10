package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.CartInfo_DTO;
import com.NorbertVarga.SpringBootDemoApp.service.CartService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private CartService cartService;

    public PurchaseController(CartService cartService) {
        this.cartService = cartService;
    }


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/mycart")
    public CartInfo_DTO getMyCartInfo() {
        return cartService.getCartInfoFromSession();
    }
}
