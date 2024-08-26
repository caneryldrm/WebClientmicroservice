package com.example.productMicroService.service;

import com.example.productMicroService.model.Product;
import com.example.productMicroService.model.ProductResponse;
import com.example.productMicroService.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public ProductResponse getProductById(int productId) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return mapper.map(productOptional.get(), ProductResponse.class);
        } else {
            // Handle the case where the product is not found
            // You can throw an exception or return null based on your requirements
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    public Product createOrUpdateProduct(Product product) {
        // Validate the categoryId by calling the Category Service
        WebClient webClient = webClientBuilder.build();
        String categoryServiceUrl = "http://localhost:8081/api/category/" + product.getCategoryId() + "/validate";

        Boolean isValidCategory = webClient.get()
                .uri(categoryServiceUrl)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();  // Blocking for simplicity in this case, but consider using non-blocking for reactive chains

        if (isValidCategory != null && isValidCategory) {
            // If the category is valid, proceed with saving the product
            return productRepository.save(product);
        } else {
            // Handle the case where the categoryId is invalid
            throw new IllegalArgumentException("Invalid category ID: " + product.getCategoryId());
        }
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }


}
