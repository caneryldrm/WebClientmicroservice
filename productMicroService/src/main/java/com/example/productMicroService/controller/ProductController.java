package com.example.productMicroService.controller;

import com.example.productMicroService.model.CreateProductRequest;
import com.example.productMicroService.model.Product;
import com.example.productMicroService.model.GetProductByIdResponse;
import com.example.productMicroService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    private ResponseEntity<GetProductByIdResponse> getProductDetails(@PathVariable("productId") int productId) throws Exception {
        GetProductByIdResponse product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/{productId}/category")
    private ResponseEntity<String> getProductCategoryName(@PathVariable("productId") int productId) throws Exception {
        String categoryName = productService.getProductCategoryNameByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryName);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product createdProduct = productService.createOrUpdateProduct(createProductRequest);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody CreateProductRequest createProductRequest) {
        createProductRequest.setProductId(id);
        Product updatedProduct = productService.createOrUpdateProduct(createProductRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted with ID: " + id);
    }

    @PutMapping("/generateBarcode/{productId}")
    public ResponseEntity<Product> createBarcodeForProduct(@PathVariable int productId) {
        Product barcodedProduct = productService.createBarcodeForProduct(productId);
        return ResponseEntity.ok(barcodedProduct);
    }

}
