package com.example.barcodeMicroService.controller;

import com.example.barcodeMicroService.model.BarcodeRequest;
import com.example.barcodeMicroService.model.BarcodeResponse;
import com.example.barcodeMicroService.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeController {

    @Autowired
    private BarcodeService barcodeService;

    @PostMapping("/generate")
    public ResponseEntity<List<BarcodeResponse>> generateBarcodes(@RequestBody BarcodeRequest request) {
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(
                request.getProductId(), request.getCategory(), request.getUnit());
        return ResponseEntity.status(HttpStatus.OK).body(barcodeResponses);
    }

}
