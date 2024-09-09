package com.example.barcodeMicroService;

import lombok.Getter;

@Getter
public enum BarcodeTypeEnum {
    KASA("kasa"),
    TERAZI("terazi"),

    URUN("ürün");

    private final String value;

    BarcodeTypeEnum(String value) {
        this.value = value;
    }


}
