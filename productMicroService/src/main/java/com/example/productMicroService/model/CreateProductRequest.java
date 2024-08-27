package com.example.productMicroService.model;

import com.example.productMicroService.BrandEnum;
import com.example.productMicroService.UnitEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateProductRequest {
    private int productId;
    private String productName;
    private int categoryId;
    private UnitEnum unit;
    private BrandEnum brand;
}
