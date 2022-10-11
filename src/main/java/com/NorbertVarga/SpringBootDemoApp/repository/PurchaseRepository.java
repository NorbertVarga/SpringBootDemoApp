package com.NorbertVarga.SpringBootDemoApp.repository;

import com.NorbertVarga.SpringBootDemoApp.entity.purchase_cart.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseItem, Long> {
}
