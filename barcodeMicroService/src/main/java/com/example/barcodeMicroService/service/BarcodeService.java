package com.example.barcodeMicroService.service;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import com.example.barcodeMicroService.model.Barcode;
import com.example.barcodeMicroService.model.BarcodeRequest;
import com.example.barcodeMicroService.model.BarcodeResponse;
import com.example.barcodeMicroService.repository.BarcodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BarcodeService {

    private final AtomicInteger sequence = new AtomicInteger(100); // For generating the sequence part of the barcode
    @Autowired
    private BarcodeRepository barcodeRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestClient restClient;

    public List<BarcodeResponse> generateAndSaveBarcodes(int productId, int category, String unit) {
        // Use RestClient to call the Category Service
        String categoryServiceUrl = "http://localhost:8081/api/category/{category}";

        try {
            String categoryName = restClient.get()
                    .uri(categoryServiceUrl, category)
                    .retrieve()
                    .body(String.class);  // Retrieve the response as a String

            // Print the category name
            System.out.println("Category Name: " + categoryName);

        } catch (Exception e) {
            // Handle any errors
            System.err.println("Error occurred: " + e.getMessage());
        }

        List<BarcodeResponse> barcodeResponses = new ArrayList<>();

        String productCode = String.format("%05d", productId); // Assuming the product code is the productId formatted to 5 digits

        if (category == 2 && "Kilogram".equalsIgnoreCase(unit)) {  // Meyve
            barcodeResponses.add(createAndSaveBarcode(productId, BarcodeTypeEnum.URUN));
            barcodeResponses.add(createAndSaveBarcode(productId, BarcodeTypeEnum.KASA));
        } else if (category == 5) {  // Balık
            if ("Kilogram".equalsIgnoreCase(unit)) {
                barcodeResponses.add(createAndSaveBarcode(productId, BarcodeTypeEnum.URUN));
                barcodeResponses.add(createAndSaveScaleBarcode(productId, productCode));
            } else if ("Adet".equalsIgnoreCase(unit)) {
                barcodeResponses.add(createAndSaveBarcode(productId, BarcodeTypeEnum.KASA));
            }
        } else if (category == 3) {  // Et
            barcodeResponses.add(createAndSaveScaleBarcode(productId, productCode));
        } else {
            barcodeResponses.add(createAndSaveBarcode(productId, BarcodeTypeEnum.URUN));
        }

        return barcodeResponses;
    }


    private BarcodeResponse createAndSaveBarcode(int productId, BarcodeTypeEnum barcodeType) {
        BarcodeResponse barcodeResponse = new BarcodeResponse();
        barcodeResponse.setProductId(productId);
        barcodeResponse.setBarcodeType(barcodeType);

        switch (barcodeType) {
            case URUN:
                barcodeResponse.setBarcode(String.format("%09d", (int) (Math.random() * 1_000_000_000)));  // 9-digit product barcode
                break;
            case KASA:
                barcodeResponse.setBarcode(String.format("%04d", (int) (Math.random() * 10_000)));  // 4-digit case barcode
                break;
            default:
                throw new IllegalArgumentException("Unsupported barcode type: " + barcodeType);
        }

        saveBarcodeToDatabase(productId, barcodeResponse);
        return barcodeResponse;
    }

    private BarcodeResponse createAndSaveScaleBarcode(int productId, String productCode) {
        BarcodeResponse barcodeResponse = new BarcodeResponse();
        barcodeResponse.setProductId(productId);
        barcodeResponse.setBarcodeType(BarcodeTypeEnum.TERAZI);

        int sequencePart = sequence.getAndIncrement() % 1000;
        barcodeResponse.setBarcode(productCode + String.format("%03d", sequencePart));  // 8-character scale barcode

        saveBarcodeToDatabase(productId, barcodeResponse);
        return barcodeResponse;
    }

    private void saveBarcodeToDatabase(int productId, BarcodeResponse barcodeResponse) {
        Barcode barcodeEntity = mapper.map(barcodeResponse, Barcode.class);
        barcodeEntity.setProductId(productId);
        barcodeRepository.save(barcodeEntity);
    }
}
