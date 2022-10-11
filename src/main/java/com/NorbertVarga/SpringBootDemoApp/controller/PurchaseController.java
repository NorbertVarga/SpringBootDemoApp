package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.CartInfo_DTO;
import com.NorbertVarga.SpringBootDemoApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private CartService cartService;

    @Autowired
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
    public ResponseEntity<CartInfo_DTO> addOneProductToCart(@PathVariable(value = "productId") Long productId) {
        return new ResponseEntity<>(cartService.addProductsToCart(productId, 1), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/add-more-product")
    public ResponseEntity<CartInfo_DTO> addMoreProductsToCart(@RequestParam (value = "productId") Long productId, @RequestParam(value = "quantity") int quantity) {
        return new ResponseEntity<>(cartService.addProductsToCart(productId,quantity), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/remove-one-product/{productId}")
    public ResponseEntity<CartInfo_DTO> removeOneProductFromCart(@PathVariable(value = "productId") Long productId) {
        return new ResponseEntity<>(cartService.removeProductsFromCart(productId, 1), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/remove-more-product")
    public ResponseEntity<CartInfo_DTO> removeMoreProductsFromCart(@RequestParam (value = "productId") Long productId, @RequestParam(value = "quantity") int quantity) {
        return new ResponseEntity<>(cartService.removeProductsFromCart(productId,quantity), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/remove-one-order/{productId}")
    public ResponseEntity<CartInfo_DTO> removeProductOrderFromCart(@PathVariable(value = "productId") Long productId) {
        return new ResponseEntity<>(cartService.removeProductEntryFromCart(productId), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/clear-cart")
    public ResponseEntity<CartInfo_DTO> clearCart() {
        return new ResponseEntity<>(cartService.clearCart(), HttpStatus.OK);
    }
}
