package com.example.productMicroService.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarcodeResponse {
    private int barcodeId;
    private String barcode;
    private String barcodeType;
    private int productId;

}
