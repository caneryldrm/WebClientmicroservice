package com.example.productMicroService.model;

import com.example.productMicroService.BrandEnum;
import com.example.productMicroService.UnitEnum;
import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "category_id", nullable = false)
    private int categoryId;
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false)
    private UnitEnum unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
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
