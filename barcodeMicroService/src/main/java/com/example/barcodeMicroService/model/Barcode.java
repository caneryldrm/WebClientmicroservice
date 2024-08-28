package com.example.barcodeMicroService.model;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "barcode")
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int barcodeId;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "barcode_type", nullable = false)
    private BarcodeTypeEnum barcodeType;

    @Column(name = "product_id", nullable = false)
    private int productId;

}
