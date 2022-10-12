package com.NorbertVarga.SpringBootDemoApp.repository;

import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.PurchaseItem;
import com.NorbertVarga.SpringBootDemoApp.entity.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findAllByUserAccountOrderByCreatedAt(UserAccount userAccount);

}
