package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;


import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.ProductOrder;

public class ProductOrderListItem_DTO {

    private String productName;
    private int quantity;
    private int totalPrice;

    public ProductOrderListItem_DTO() {
    }

    public ProductOrderListItem_DTO(String productName, int quantity, int totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public ProductOrderListItem_DTO(ProductOrder order) {
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
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
