package com.example.barcodeMicroService.model;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import jakarta.persistence.*;

public class BarcodeResponse {
    private int barcodeId;
    private String barcode;
    private BarcodeTypeEnum barcodeType;
    private int productId;

    public int getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(int barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BarcodeTypeEnum getBarcodeType() {
        return barcodeType;
    }

    public void setBarcodeType(BarcodeTypeEnum barcodeType) {
        this.barcodeType = barcodeType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
