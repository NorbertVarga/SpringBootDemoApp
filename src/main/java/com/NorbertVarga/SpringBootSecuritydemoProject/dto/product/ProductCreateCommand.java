package com.NorbertVarga.SpringBootSecuritydemoProject.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductCreateCommand {

    private String name;
    private String description;

    @JsonProperty(value = "tags")
    private List<String> tags;

    private int price;
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
