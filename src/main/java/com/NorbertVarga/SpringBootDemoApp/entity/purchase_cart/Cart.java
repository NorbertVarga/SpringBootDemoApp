package com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.ProductOrderListItemInCart_DTO;
import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private HashMap<Product, Integer> productOrders = new HashMap<>();
    private int totalPrice;
    private UserAccount user;

    public Cart() {
    }

    public Cart(UserAccount user) {
        this.totalPrice = 0;
        this.user = user;
    }

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

    public int calculateTotalQuantity() {
        int sumQuantity = 0;
        for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
            sumQuantity += entry.getValue();
        }
        return sumQuantity;
    }

    public List<ProductOrderListItemInCart_DTO> mapEntriesToDto() {
        List<ProductOrderListItemInCart_DTO> productOrderDtoList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
            ProductOrderListItemInCart_DTO productOrderDto = new ProductOrderListItemInCart_DTO();
            productOrderDto.setProductName(entry.getKey().getName());
            productOrderDto.setQuantity(entry.getValue());
            productOrderDto.setTotalPrice(calculatePriceForEntry(entry));
            productOrderDtoList.add(productOrderDto);
        }
        return productOrderDtoList;
    }

    public int calculatePriceForEntry(Map.Entry<Product, Integer> entry) {
        return entry.getKey().getPrice() * entry.getValue();
    }

    public HashMap<Product, Integer> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(HashMap<Product, Integer> productOrders) {
        this.productOrders = productOrders;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
