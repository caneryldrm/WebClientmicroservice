package com.example.productMicroService.model;

import com.example.productMicroService.BrandEnum;
import com.example.productMicroService.UnitEnum;
import jakarta.persistence.*;

public class ProductResponse {
    private int productId;
    private String productName;

    private int categoryId;
    private UnitEnum unit;
    private BrandEnum brand;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public BrandEnum getBrand() {
        return brand;
    }

    public void setBrand(BrandEnum brand) {
        this.brand = brand;
    }


}
