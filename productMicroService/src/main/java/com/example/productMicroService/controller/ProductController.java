package com.example.productMicroService.controller;

import com.example.productMicroService.model.Product;
import com.example.productMicroService.model.ProductResponse;
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
    private ResponseEntity<ProductResponse> getProductDetails(@PathVariable("productId") int productId) throws Exception {
        ProductResponse product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createOrUpdateProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setProductId(id);
        Product updatedProduct = productService.createOrUpdateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted with ID: " + id);
    }
}
