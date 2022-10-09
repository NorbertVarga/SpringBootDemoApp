package com.NorbertVarga.SpringBootDemoApp.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductUpdateCommand {

    private String name;

    @Size(max = 1000, message =
            "Product description cannot be more than 1000 characters")
    private String description;

    @Min(value = 0, message = "Price cannot be negative number")
    private Integer price;

    @Min(value = 0, message = "Total quantity cannot be negative number")
    private Integer totalQuantity;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
