package com.example.productMicroService.model;

import com.example.productMicroService.UnitEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarcodeRequest {

        private int productId;
        private int categoryId;
        private UnitEnum unit;
        private String productCode;

}
