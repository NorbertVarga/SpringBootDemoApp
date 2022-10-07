package com.NorbertVarga.SpringBootSecuritydemoProject.dto.product;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.product.Product;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ProductData_DTO {

    private Long productId;
    private String name;
    private String description;
    private List<String> tags;
    private int price;
    private int totalQuantity;
    private String createdAt;
    private String lastModified;

    public ProductData_DTO() {
    }

    public ProductData_DTO(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.tags = product.getTags();
        this.price = product.getPrice();
        this.totalQuantity = product.getTotalQuantity();
        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(product.getCreatedAt());

        this.lastModified = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(product.getLastModified());
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public List<String> getTags() {
        return tags;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
