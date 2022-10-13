package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;


import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.ProductOrder;

import java.util.Map;

public class ProductOrderListItem_DTO {

    private String productName;
    private int quantity;
    private int totalPrice;

    public ProductOrderListItem_DTO() {
    }

    // Used to display the order entries in the cart
    public ProductOrderListItem_DTO(Map.Entry<Product, Integer> entryFromCart) {
        this.productName = entryFromCart.getKey().getName();
        this.quantity = entryFromCart.getValue();
        this.totalPrice = entryFromCart.getKey().getPrice() * entryFromCart.getValue();
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
