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
    private Integer price;

    @NotNull
    @Min(value = 0, message = "Total quantity cannot be negative number")
    private Integer totalQuantity;

    public ProductCreateCommand() {
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
