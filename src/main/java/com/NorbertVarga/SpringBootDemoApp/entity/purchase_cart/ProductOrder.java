package com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart;

import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id", nullable = false)
    private Long productOrderId;

    @OneToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private PurchaseItem purchaseItem;

    @Column(name = "product_order_quantity")
    @NotNull
    @Min(1)
    private int quantity;

    @Column(name = "product_order_total_price")
    @NotNull
    private int totalPrice;

    @Column(name = "product_order_created_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    public ProductOrder() {
    }

    // Used in the PurchaseService to map the cart entries to Entity
    public ProductOrder(Map.Entry<Product, Integer> cartEntry) {
        this.product = cartEntry.getKey();
        this.quantity = cartEntry.getValue();
        this.totalPrice = cartEntry.getKey().getPrice() * quantity;
    }

    public ProductOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseItem getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
