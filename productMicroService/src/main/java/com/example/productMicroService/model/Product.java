package com.example.productMicroService.model;

import com.example.productMicroService.BrandEnum;
import com.example.productMicroService.UnitEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Setter
@Entity
@Getter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "product_code", nullable = false, length = 5)
    private String productCode  ;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false)
    private UnitEnum unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private BrandEnum brand;

    @Column(name = "barcode1")
    private String barcode1;

    @Column(name = "barcode2")
    private String barcode2;


}
