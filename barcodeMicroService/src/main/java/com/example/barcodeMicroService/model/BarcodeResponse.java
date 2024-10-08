package com.example.barcodeMicroService.model;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarcodeResponse {
    private int barcodeId;
    private String barcode;
    private BarcodeTypeEnum barcodeType;
    private int productId;

}
