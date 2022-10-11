package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.PurchaseItem;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseItemData_DTO {

    private String userFullName;
    private String userEmail;
    private List<ProductOrderData_DTO> productOrders = new ArrayList<>();
    private int totalPrice;
    private int totalQuantity;
    private String createdAt;

    public PurchaseItemData_DTO() {
    }

    public PurchaseItemData_DTO(PurchaseItem purchaseItem) {
        this.userFullName = purchaseItem.getUserAccount().getFirstName() + " " + purchaseItem.getUserAccount().getLastName();
        this.userEmail = purchaseItem.getUserAccount().getEmail();
        this.productOrders = purchaseItem.getProductOrderList()
                .stream()
                .map(ProductOrderData_DTO::new)
                .collect(Collectors.toList());
        this.totalPrice = purchaseItem.getTotalPrice();
        this.totalQuantity = purchaseItem.getTotalQuantity();
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(purchaseItem.getCreatedAt());
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<ProductOrderData_DTO> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrderData_DTO> productOrders) {
        this.productOrders = productOrders;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
