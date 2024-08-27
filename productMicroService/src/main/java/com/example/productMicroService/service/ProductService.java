package com.example.productMicroService.service;

import com.example.productMicroService.model.CreateProductRequest;
import com.example.productMicroService.model.GetCategoryNameResponse;
import com.example.productMicroService.model.Product;
import com.example.productMicroService.model.GetProductByIdResponse;
import com.example.productMicroService.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestClient restClient;

    public GetProductByIdResponse getProductById(int productId) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return mapper.map(productOptional.get(), GetProductByIdResponse.class);
        } else {
            // Handle the case where the product is not found
            // You can throw an exception or return null based on your requirements
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    public String getProductCategoryNameByProductId(int productId) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int categoryId = product.getCategoryId();  // Get the categoryId from the product

            // Use RestClient to call the Category Service
            String categoryServiceUrl = "http://localhost:8081/api/category/{categoryId}";
            GetCategoryNameResponse getCategoryNameResponse = restClient.get()
                    .uri(categoryServiceUrl, categoryId)  // Pass categoryId as a path variable
                    .retrieve()
                    .body(GetCategoryNameResponse.class);  // Retrieve the response as a String


            // Retrieve the category name using RestClient
            assert getCategoryNameResponse != null;
            return "Your product category is: " + getCategoryNameResponse.getCategoryName();
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }


    public Product createOrUpdateProduct(CreateProductRequest createProductRequest) {
        // Validate the categoryId by calling the Category Service
        String categoryServiceValidateUrl = "http://localhost:8081/api/category/{categoryId}/validate";

        Boolean isValidCategory = restClient.get()
                .uri(categoryServiceValidateUrl, createProductRequest.getCategoryId())  // Pass categoryId as a path variable
                .retrieve()
                .body(Boolean.class);  // Retrieve the response as a Boolean

        if (isValidCategory != null && isValidCategory) {
            String categoryServiceUrl = "http://localhost:8081/api/category/{categoryId}";
            GetCategoryNameResponse getCategoryNameResponse = restClient.get()
                    .uri(categoryServiceUrl, createProductRequest.getCategoryId())  // Pass categoryId as a path variable
                    .retrieve()
                    .body(GetCategoryNameResponse.class);  // Retrieve the response as a String


            // Retrieve the category name using RestClient
            assert getCategoryNameResponse != null;
            // If the category is valid, proceed with saving the product
            String firstTwo = getCategoryNameResponse.getCategoryName().substring(0, 2).toUpperCase();
            String lastThree = RandomStringUtils.randomAlphabetic(3).toUpperCase();
            String productCode = firstTwo + lastThree;
            Product product = mapper.map(createProductRequest, Product.class);
            product.setProductCode(productCode);
            product.setBarcode1(null);
            product.setBarcode2(null);
            return productRepository.save(product);
        } else {
            // Handle the case where the categoryId is invalid
            throw new IllegalArgumentException("Invalid category ID: " + createProductRequest.getCategoryId());
        }
    }


    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }


}
