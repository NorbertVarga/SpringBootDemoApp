package com.NorbertVarga.SpringBootDemoApp.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductUpdateCommand {

    private String name;

    @Size(max = 1000, message =
            "Product description cannot be more than 1000 characters")
    private String description;

    @Min(value = 0, message = "Price cannot be negative number")
    private int price;

    @Min(value = 0, message = "Total quantity cannot be negative number")
    private int totalQuantity;

    public ProductUpdateCommand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
