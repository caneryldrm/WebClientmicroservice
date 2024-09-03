package com.example.productMicroService;

import com.example.productMicroService.controller.ProductController;
import com.example.productMicroService.model.CreateProductRequest;
import com.example.productMicroService.model.Product;
import com.example.productMicroService.model.GetProductByIdResponse;
import com.example.productMicroService.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductDetails() throws Exception {
        int productId = 1;
        GetProductByIdResponse productResponse = new GetProductByIdResponse();
        when(productService.getProductById(productId)).thenReturn(productResponse);

        ResponseEntity<GetProductByIdResponse> response = productController.getProductDetails(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductCategoryName() throws Exception {
        int productId = 1;
        String categoryName = "Bakliyat";
        when(productService.getProductCategoryNameByProductId(productId)).thenReturn(categoryName);

        ResponseEntity<String> response = productController.getProductCategoryName(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryName, response.getBody());
        verify(productService, times(1)).getProductCategoryNameByProductId(productId);
    }

    @Test
    void testCreateProduct() {
        CreateProductRequest createProductRequest = new CreateProductRequest();
        Product product = new Product();
        when(productService.createOrUpdateProduct(createProductRequest)).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(createProductRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createOrUpdateProduct(createProductRequest);
    }

    @Test
    void testUpdateProduct() {
        int productId = 1;
        CreateProductRequest createProductRequest = new CreateProductRequest();
        Product product = new Product();
        when(productService.createOrUpdateProduct(createProductRequest)).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(productId, createProductRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createOrUpdateProduct(createProductRequest);
    }

    @Test
    void testDeleteProduct() {
        int productId = 1;
        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<String> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted with ID: " + productId, response.getBody());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testCreateBarcodeForProduct() {
        int productId = 1;
        Product product = new Product();
        when(productService.createBarcodeForProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.createBarcodeForProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createBarcodeForProduct(productId);
    }
}
