package com.example.productMicroService;

public enum UnitEnum {
    KILOGRAM("kilogram"),
    UNIT("unit");

    private final String value;

    UnitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
