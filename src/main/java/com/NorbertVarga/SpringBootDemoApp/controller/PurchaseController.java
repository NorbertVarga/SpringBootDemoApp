package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.CartInfo_DTO;
import com.NorbertVarga.SpringBootDemoApp.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CartInfo_DTO> getMyCartInfo() {
        return new ResponseEntity<>(cartService.getCartInfoFromSession(), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/add-one-product/{productId}")
    public ResponseEntity<CartInfo_DTO> addOneProductToCart(@PathVariable (value = "productId") Long productId) {
        return new ResponseEntity<>(cartService.addProductsToCart(productId, 1), HttpStatus.OK);
    }
}
