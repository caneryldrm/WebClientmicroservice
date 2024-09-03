package com.example.productMicroService;

import com.example.productMicroService.config.WireMockConfig;
import com.example.productMicroService.model.*;
import com.example.productMicroService.repository.ProductRepository;
import com.example.productMicroService.service.ProductService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(WireMockConfig.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductService productService;

    private WireMockServer wireMockServer;

    private WireMock wireMock;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @Test
    void testGetProductById() throws Exception {
        int productId = 1;
        Product product = new Product();
        GetProductByIdResponse response = new GetProductByIdResponse();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.map(product, GetProductByIdResponse.class)).thenReturn(response);

        GetProductByIdResponse result = productService.getProductById(productId);

        assertEquals(response, result);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductCategoryNameByProductId() throws Exception {
        int productId = 1;
        Product product = new Product();
        product.setCategoryId(2);

        GetCategoryNameResponse categoryResponse = new GetCategoryNameResponse();
        categoryResponse.setCategoryName("Electronics");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // WireMock stubbing
        stubFor(get(urlPathMatching("/categories/2"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{ \"categoryName\": \"Electronics\" }")));

        String result = productService.getProductCategoryNameByProductId(productId);

        assertEquals("Your product category is: Electronics", result);
        verify(productRepository, times(1)).findById(productId);

        // Verify WireMock
        verify(getRequestedFor(urlPathMatching("/categories/2")));
    }

    @Test
    void testCreateOrUpdateProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setCategoryId(2);

        GetCategoryNameResponse categoryResponse = new GetCategoryNameResponse();
        categoryResponse.setCategoryName("Electronics");

        Product savedProduct = new Product();
        savedProduct.setProductCode("ELABC");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(mapper.map(request, Product.class)).thenReturn(new Product());

        // WireMock stubbing
        stubFor(get(urlPathMatching("/categories/2"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{ \"categoryName\": \"Electronics\" }")));

        stubFor(get(urlPathMatching("/validate-category/2"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("true")));

        Product result = productService.createOrUpdateProduct(request);

        assertEquals(savedProduct, result);

        // Verify WireMock
        verify(getRequestedFor(urlPathMatching("/categories/2")));
        verify(getRequestedFor(urlPathMatching("/validate-category/2")));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        int productId = 4;

        doNothing().when(productRepository).deleteById(productId);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testCreateBarcodeForProduct() {
        int productId = 4;
        Product product = new Product();
        product.setCategoryId(2);
        product.setUnit(UnitEnum.KILOGRAM);
        product.setProductCode("MEHKU");

        BarcodeResponse barcodeResponse1 = new BarcodeResponse();
        barcodeResponse1.setBarcode("114460740");

        BarcodeResponse barcodeResponse2 = new BarcodeResponse();
        barcodeResponse2.setBarcode("5760");

        ArrayList<BarcodeResponse> barcodeResponses = new ArrayList<>();
        barcodeResponses.add(barcodeResponse1);
        barcodeResponses.add(barcodeResponse2);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        // WireMock stubbing
//        stubFor(post(urlPathMatching("/barcodes"))
//                .withRequestBody(equalToJson("{ \"productId\": 4, \"categoryId\": 2, \"unit\": \"KILOGRAM\", \"productCode\": \"MEHKU\" }"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                        .withBody("[{ \"barcode\": \"114460740\" }, { \"barcode\": \"5760\" }]")));

        wireMock.stubFor(get(urlEqualTo("/api/resource"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"key\": \"value\" }")));

        // Make a call to the RestClient
        String response = restTemplate.getForObject("/api/resource", String.class);

        Product result = productService.createBarcodeForProduct(productId);

        assertEquals("114460740", result.getBarcode1());
        assertEquals("5760", result.getBarcode2());

        // Verify WireMock
        verify(postRequestedFor(urlPathMatching("/barcodes")));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
