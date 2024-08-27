package com.example.barcodeMicroService;

public enum BarcodeTypeEnum {
    KASA("kasa"),
    TERAZI("terazi"),

    URUN("ürün");

    private final String value;

    BarcodeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }



}
