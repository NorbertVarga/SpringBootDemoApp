package com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartInfo_DTO {

    private String userEmail;
    private int productsTotalPrice;
    private int productsTotalQuantity;
    private List<ProductOrderListItem_DTO> productOrders = new ArrayList<>();

    public CartInfo_DTO(Cart cart) {
        this.userEmail = cart.getUser().getEmail();
        this.productsTotalPrice = cart.getTotalPrice();
        this.productsTotalQuantity = cart.calculateTotalQuantity();
        this.productOrders = cart.mapEntriesToDto();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getProductsTotalPrice() {
        return productsTotalPrice;
    }

    public void setProductsTotalPrice(int productsTotalPrice) {
        this.productsTotalPrice = productsTotalPrice;
    }

    public int getProductsTotalQuantity() {
        return productsTotalQuantity;
    }

    public void setProductsTotalQuantity(int productsTotalQuantity) {
        this.productsTotalQuantity = productsTotalQuantity;
    }

    public List<ProductOrderListItem_DTO> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrderListItem_DTO> productOrders) {
        this.productOrders = productOrders;
    }
}
