package com.NorbertVarga.SpringBootSecuritydemoProject.service;

import com.NorbertVarga.SpringBootSecuritydemoProject.faker.FakerService;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    private FakerService faker;
    private ProductRepository productRepository;

    public ProductService(FakerService faker, ProductRepository productRepository) {
        this.faker = faker;
        this.productRepository = productRepository;
    }


}
