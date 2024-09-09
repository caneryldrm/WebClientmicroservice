package com.example.productMicroService;

import lombok.Getter;

@Getter
public enum UnitEnum {
    KILOGRAM("kilogram"),
    UNIT("unit");

    private final String value;

    UnitEnum(String value) {
        this.value = value;
    }

}
