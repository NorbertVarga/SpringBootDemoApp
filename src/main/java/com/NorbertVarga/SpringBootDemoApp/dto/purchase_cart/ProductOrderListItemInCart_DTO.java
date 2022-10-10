package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;


public class ProductOrderListItemInCart_DTO {

    private String productName;
    private int quantity;
    private int totalPrice;

    public ProductOrderListItemInCart_DTO() {
    }

    public ProductOrderListItemInCart_DTO(String productName, int quantity, int totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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
}
