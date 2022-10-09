package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private HashMap<Product, Integer> productOrders = new HashMap<>();
    private int totalPrice = 0;


    public void addProducts(Product product, int quantity) {

        calculateTotalPrice();
    }

    public void removeProducts(Product product, int quantity) {

        calculateTotalPrice();
    }


    public void clearProduct() {
        this.productOrders.clear();
    }

    public void calculateTotalPrice() {
        int calculatedPrice = 0; // todo calculate via iterate through the entries
        this.totalPrice = calculatedPrice;
    }
}
