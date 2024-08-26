package com.example.productMicroService;

public enum BrandEnum {
    ULKER("Ülker"),
    ETI("Eti"),
    PINAR("Pınar"),
    SUTAS("Sütaş"),
    TORKU("Torku"),
    DAMLA("Damla"),
    SEK("Sek");

    private final String value;

    BrandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
