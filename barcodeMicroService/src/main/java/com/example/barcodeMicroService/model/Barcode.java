package com.example.barcodeMicroService.model;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "barcode")
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int barcodeId;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "barcode_type", nullable = false)
    private BarcodeTypeEnum barcodeType;

    @Column(name = "product_id", nullable = false)
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
