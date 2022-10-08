package com.NorbertVarga.SpringBootSecuritydemoProject.entity.purchase;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserAccount;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
// Entity Listener needed for auditing and automatically manage the creating and modified dates
@EntityListeners(AuditingEntityListener.class)
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id", nullable = false)
    private Long purchaseItemId;

    @ManyToOne // todo update userAccount entity and related DTO
    @JoinColumn(name = "user_id")
    @NotNull
    private UserAccount userAccount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_item_id")
    @NotEmpty
    private List<ProductOrder> productOrderList;

    @Column(name = "purchase_item_total_price")
    @NotNull
    private int totalPrice;

    @Column(name = "purchase_item_created_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    public PurchaseItem() {
    }

    public PurchaseItem(UserAccount userAccount, List<ProductOrder> productOrderList) {
        this.userAccount = userAccount;
        this.productOrderList = productOrderList;
        this.totalPrice = productOrderList.stream().mapToInt(ProductOrder::getTotalPrice).sum();
    }

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<ProductOrder> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrder> productOrderList) {
        this.productOrderList = productOrderList;
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
