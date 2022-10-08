package com.NorbertVarga.SpringBootSecuritydemoProject.service;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.product.ProductCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.product.ProductData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.product.Product;
import com.NorbertVarga.SpringBootSecuritydemoProject.faker.FakerService;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private FakerService faker;
    private ProductRepository productRepository;

    public ProductService(FakerService faker, ProductRepository productRepository) {
        this.faker = faker;
        this.productRepository = productRepository;
        populateDataBaseWithDummyProducts(30);
    }

    //  **  SECURED USER METHODS    **  ////////////////////////////////////////////
    public List<ProductData_DTO> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList
                .stream()
                .map(ProductData_DTO::new)
                .collect(Collectors.toList());
    }
    ////////////////////////////////////////////////////////////////////////////////

    //  **  SECURED ADMIN METHODS    **  ////////////////////////////////////////////
    public ProductData_DTO createProduct(ProductCreateCommand command) {
        Product product = new Product(command);
        Product createdProduct = productRepository.save(product);
        return new ProductData_DTO(createdProduct);
    }
    ////////////////////////////////////////////////////////////////////////////////

    //  **  UTILS   **  ////////////////////////////////////////////////////////////
    private void populateDataBaseWithDummyProducts(int count) {
        List<Product> productList = faker.createDummyProducts(count);
        productRepository.saveAll(productList);
    }

    ////////////////////////////////////////////////////////////////////////////////
}
