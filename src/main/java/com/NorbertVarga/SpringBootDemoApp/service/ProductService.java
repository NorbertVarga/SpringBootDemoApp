package com.NorbertVarga.SpringBootDemoApp.service;

import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductCreateCommand;
import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductData_DTO;
import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductUpdateCommand;
import com.NorbertVarga.SpringBootDemoApp.entity.product.Product;
import com.NorbertVarga.SpringBootDemoApp.faker.FakerService;
import com.NorbertVarga.SpringBootDemoApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    public ProductData_DTO getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return new ProductData_DTO(productOptional.get());
        } else {
            throw new EntityNotFoundException("There is no Product with the given Id");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    //  **  SECURED ADMIN METHODS    **  ////////////////////////////////////////////
    public ProductData_DTO createProduct(ProductCreateCommand command) {
        Product product = new Product(command);
        Product createdProduct = productRepository.save(product);
        return new ProductData_DTO(createdProduct);
    }

    public ProductData_DTO updateProductById(ProductUpdateCommand command, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(command.getName());
            product.setDescription(command.getDescription());
            product.setPrice(command.getPrice());
            product.setTotalQuantity(command.getTotalQuantity());
            Product updatedProduct = productRepository.save(product);
            return new ProductData_DTO(updatedProduct);
        } else {
            throw new EntityNotFoundException("There is no Product with the given Id");
        }
    }

    public void deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new EntityNotFoundException("There is no Product with the given Id");
        }
    }
    ////////////////////////////////////////////////////////////////////////////////

    //  **  UTILS   **  ////////////////////////////////////////////////////////////
    private void populateDataBaseWithDummyProducts(int count) {
        List<Product> productList = faker.createDummyProducts(count);
        productRepository.saveAll(productList);
    }


    ////////////////////////////////////////////////////////////////////////////////
}
