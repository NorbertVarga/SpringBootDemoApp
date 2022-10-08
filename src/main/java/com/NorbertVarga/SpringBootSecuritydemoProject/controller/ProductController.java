package com.NorbertVarga.SpringBootSecuritydemoProject.controller;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.product.ProductCreateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.product.ProductData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.dto.product.ProductUpdateCommand;
import com.NorbertVarga.SpringBootSecuritydemoProject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //  **  SECURED USER ENDPOINTS  **  //////////////////////////////////////////////////////////
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<ProductData_DTO>> getAllProducts() {
        List<ProductData_DTO> productDataList = productService.getAllProduct();
        return new ResponseEntity<>(productDataList, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<ProductData_DTO> getProductById(@PathVariable(value = "id") Long id) {
        ProductData_DTO productData = productService.getProductById(id);
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    /////////////////////////////////////////////////////////////////////////////////////

    //  **  SECURED ADMIN ENDPOINTS  ** //////////////////////////////////////////////////////////
    @PostMapping("/create")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<ProductData_DTO> createProductByAdmin(@RequestBody ProductCreateCommand productCreateCommandByAdmin) {
        ProductData_DTO createdProductData = productService.createProduct(productCreateCommandByAdmin);
        return new ResponseEntity<>(createdProductData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<ProductData_DTO> updateProductById(@RequestBody ProductUpdateCommand productUpdateCommand, @PathVariable(value = "id") Long id) {
        ProductData_DTO updatedProductData = productService.updateProductById(productUpdateCommand, id);
        return new ResponseEntity<>(updatedProductData, HttpStatus.OK);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
}
