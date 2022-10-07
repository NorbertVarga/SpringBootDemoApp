package com.NorbertVarga.SpringBootSecuritydemoProject.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "product_id")
    private Long productId;

    @Column (name = "product_name")
    private String name;

    @Column (name = "product_description")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @OrderColumn
    private List<String> tags = new ArrayList<>();

    @Column (name = "product_price")
    private Integer price;

    @Column(name = "product_created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "product_last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;

    public Product() {
    }

    public Product(String name, String description, List<String> tags, Integer price) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
