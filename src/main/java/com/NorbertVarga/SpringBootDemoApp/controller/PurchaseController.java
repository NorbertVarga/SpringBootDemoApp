package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.PurchaseItemData_DTO;
import com.NorbertVarga.SpringBootDemoApp.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    //  **  SECURED USER ENDPOINTS  //////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////

    //  **  SECURED ADMIN ENDPOINTS ///////////////////////////////////////////////
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/get-all")
    public ResponseEntity<List<PurchaseItemData_DTO>> getAllPurchaseItem() {
        List<PurchaseItemData_DTO> purchaseItems = purchaseService.getAllPurchaseItemData();
        return new ResponseEntity<>(purchaseItems, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<List<PurchaseItemData_DTO>> getAllPurchaseItemByUser(@PathVariable (value = "userId") Long userId) {
        List<PurchaseItemData_DTO> purchaseItems = purchaseService.getAllPurchaseItemDataByUser(userId);
        return new ResponseEntity<>(purchaseItems, HttpStatus.OK);
    }

    ///////////////////////////////////////////////////////////////////////////////
}
