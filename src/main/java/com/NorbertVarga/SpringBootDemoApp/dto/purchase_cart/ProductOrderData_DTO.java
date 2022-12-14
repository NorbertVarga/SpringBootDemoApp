package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.ProductOrder;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ProductOrderData_DTO {

    private String userEmail;
    private long purchaseId;
    private String productName;
    private int quantity;
    private int totalPrice;
    private String createdAt;

    public ProductOrderData_DTO() {
    }

    public ProductOrderData_DTO(ProductOrder order) {
        this.userEmail = order.getPurchaseItem().getUserAccount().getEmail();
        this.purchaseId = order.getPurchaseItem().getPurchaseItemId();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(order.getCreatedAt());
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
