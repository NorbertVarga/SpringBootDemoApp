package com.NorbertVarga.SpringBootDemoApp.controller;

import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductCreateCommand;
import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductData_DTO;
import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductUpdateCommand;
import com.NorbertVarga.SpringBootDemoApp.service.ProductService;
import com.NorbertVarga.SpringBootDemoApp.validation.ProductUpdateCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductUpdateCommandValidator productUpdateCommandValidator;

    @Autowired
    public ProductController(ProductService productService, ProductUpdateCommandValidator productUpdateCommandValidator) {
        this.productService = productService;
        this.productUpdateCommandValidator = productUpdateCommandValidator;
    }

    @InitBinder("productUpdateCommand")
    protected void initBinderForProductUpdateCommand(WebDataBinder binder) {
        binder.addValidators(productUpdateCommandValidator);
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
        ProductData_DTO productData = productService.getProductInfoById(id);
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    /////////////////////////////////////////////////////////////////////////////////////

    //  **  SECURED ADMIN ENDPOINTS  ** //////////////////////////////////////////////////////////
    @PostMapping("/create")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<ProductData_DTO> createProductByAdmin(@RequestBody @Valid ProductCreateCommand productCreateCommandByAdmin) {
        ProductData_DTO createdProductData = productService.createProduct(productCreateCommandByAdmin);
        return new ResponseEntity<>(createdProductData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<ProductData_DTO> updateProductById(@RequestBody @Valid ProductUpdateCommand productUpdateCommand, @PathVariable(value = "id") Long id) {
        ProductData_DTO updatedProductData = productService.updateProductById(productUpdateCommand, id);
        return new ResponseEntity<>(updatedProductData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteProductById(@PathVariable(value = "id") Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
