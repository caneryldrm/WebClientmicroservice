package com.example.barcodeMicroService.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarcodeRequest {

    private int productId;
    private int categoryId;
    private String unit;
    private String productCode;


}
