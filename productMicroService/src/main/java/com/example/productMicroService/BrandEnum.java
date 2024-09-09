package com.example.productMicroService;

import lombok.Getter;

@Getter
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

}
