package com.NorbertVarga.SpringBootDemoApp.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductCreateCommand {

    @Size(min = 3, max = 80, message =
            "Name of the Product must be between {min} and {max} characters.")
    @NotBlank(message = "Empty string not allowed here!")
    private String name;

    @Size(max = 1000, message =
            "Product description cannot be more than 1000 characters")
    private String description;

    @JsonProperty(value = "tags")
    private List<String> tags;

    @NotNull
    @Min(value = 0, message = "Price cannot be negative number")
    private int price;

    @NotNull
    @Min(value = 0, message = "Total quantity cannot be negative number")
    private int totalQuantity;

    public ProductCreateCommand() {
    }

    public ProductCreateCommand(String name, String description, List<String> tags, int price, int totalQuantity) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.totalQuantity = totalQuantity;
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

    @JsonProperty(value = "tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty(value = "tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
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
