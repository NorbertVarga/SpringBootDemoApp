package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.repository.ProductOrderRepository;
import com.NorbertVarga.SpringBootDemoApp.repository.PurchaseRepository;
import com.NorbertVarga.SpringBootDemoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserService userService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ProductOrderRepository productOrderRepository, UserService userService) {
        this.purchaseRepository = purchaseRepository;
        this.productOrderRepository = productOrderRepository;
        this.userService = userService;
    }


}
