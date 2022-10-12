package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.PurchaseItemData_DTO;
import com.NorbertVarga.SpringBootDemoApp.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/make-purchase")
    public ResponseEntity<PurchaseItemData_DTO> makePurchase() {
        PurchaseItemData_DTO purchaseData = purchaseService.makePurchase();
        return new ResponseEntity<>(purchaseData, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/my-purchases")
    public ResponseEntity<List<PurchaseItemData_DTO>> getMyPurchases() {
        List<PurchaseItemData_DTO> myPurchasesData = purchaseService.getMyPurchases();
        return new ResponseEntity<>(myPurchasesData, HttpStatus.OK);
    }
}
