package com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.dto.purchase_cart.ProductOrderListItem_DTO;
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
import java.util.stream.Collectors;

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

    //  **  CART LOGIC METHODS  /////////////////////////////////////////////
    public void addProducts(Product product, int quantity) {
        if (this.productOrders.isEmpty()) {
            this.productOrders.put(product, quantity);
        } else {
            // We have a problem here: can't manage the entries while iterating through them
            // New Map object, we will copy the entries from the original Map and manipulate the values where needed
            HashMap<Product, Integer> updatedNewMap = new HashMap<>();
            boolean isProductOnTheCartAlready = false;
            for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
                if (entry.getKey().equals(product)) {
                    updatedNewMap.put(entry.getKey(), entry.getValue() + quantity);
                    isProductOnTheCartAlready = true;
                } else {
                    updatedNewMap.put(entry.getKey(), entry.getValue());
                }
            }
            if (!isProductOnTheCartAlready) {
                updatedNewMap.put(product, quantity);
            }
            this.productOrders.clear();
            this.productOrders.putAll(updatedNewMap);
        }
        calculateTotalPrice();
    }

    public void removeProducts(Product product, int quantity) {
        HashMap<Product, Integer> updatedNewMap = new HashMap<>();
        if (!this.productOrders.isEmpty()) {
            for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
                if (entry.getKey().equals(product)) {
                    if (entry.getValue() - quantity > 0) {
                        updatedNewMap.put(entry.getKey(), entry.getValue() - quantity);
                    }
                } else {
                    updatedNewMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        this.productOrders.clear();
        this.productOrders.putAll(updatedNewMap);
        calculateTotalPrice();
    }

    public void clearEntry(Product product) {
        if (!this.productOrders.isEmpty()) {
            Map.Entry<Product, Integer> entryToDelete = null;
            for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
                if (entry.getKey().equals(product)) {
                    entryToDelete = entry;
                    break;
                }
            }
            if (entryToDelete != null) {
                this.productOrders.remove(entryToDelete.getKey(), entryToDelete.getValue());
            }
        }
        calculateTotalPrice();
    }

    public void clearCart() {
        this.productOrders.clear();
        calculateTotalPrice();
    }
    /////////////////////////////////////////////////////////////////////////

    //  **  CALCULATIONS    ////////////////////////////////
    public void calculateTotalPrice() {
        int calculatedPrice = 0;
        if (!this.productOrders.isEmpty()) {
            for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
                calculatedPrice += calculatePriceForEntry(entry);
            }
        }
        this.totalPrice = calculatedPrice;
    }

    public int calculatePriceForEntry(Map.Entry<Product, Integer> entry) {
        return entry.getKey().getPrice() * entry.getValue();
    }

    public int calculateTotalQuantity() {
        int sumQuantity = 0;
        for (Map.Entry<Product, Integer> entry : this.productOrders.entrySet()) {
            sumQuantity += entry.getValue();
        }
        return sumQuantity;
    }
    //////////////////////////////////////////////////////////////////////////////

    public List<ProductOrderListItem_DTO> mapEntriesToDto() {
        return this.productOrders
                .entrySet()
                .stream()
                .map(ProductOrderListItem_DTO::new)
                .collect(Collectors.toList());
    }

    public List<ProductOrder> mapEntriesToProductOrderEntities() {
        List<ProductOrder> productOrders = this.productOrders
                .entrySet()
                .stream()
                .map(ProductOrder::new)
                .collect(Collectors.toList());
        return productOrders;
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
