package com.example.barcodeMicroService;

import com.example.barcodeMicroService.BarcodeTypeEnum;
import com.example.barcodeMicroService.model.Barcode;
import com.example.barcodeMicroService.model.BarcodeResponse;
import com.example.barcodeMicroService.repository.BarcodeRepository;
import com.example.barcodeMicroService.service.BarcodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BarcodeServiceTest {

    @InjectMocks
    private BarcodeService barcodeService;

    @Mock
    private BarcodeRepository barcodeRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAndSaveBarcodesForCategoryFruitWithKilogram() {
        // Arrange
        int productId = 1;
        int categoryId = 2;
        String unit = "KILOGRAM";
        String productCode = "12345";

        Barcode barcodeEntity1 = new Barcode();
        barcodeEntity1.setBarcodeId(1);
        Barcode barcodeEntity2 = new Barcode();
        barcodeEntity2.setBarcodeId(2);

        when(mapper.map(any(BarcodeResponse.class), eq(Barcode.class))).thenReturn(barcodeEntity1, barcodeEntity2);
        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcodeEntity1, barcodeEntity2);

        // Act
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(productId, categoryId, unit, productCode);

        // Assert
        assertEquals(2, barcodeResponses.size());
        assertEquals(BarcodeTypeEnum.URUN, barcodeResponses.get(0).getBarcodeType());
        assertEquals(BarcodeTypeEnum.KASA, barcodeResponses.get(1).getBarcodeType());

        verify(barcodeRepository, times(2)).save(any(Barcode.class));
        verify(mapper, times(2)).map(any(BarcodeResponse.class), eq(Barcode.class));
    }

    @Test
    void testGenerateAndSaveBarcodesForCategoryFishWithKilogram() {
        // Arrange
        int productId = 1;
        int categoryId = 5;
        String unit = "KILOGRAM";
        String productCode = "12345";

        Barcode barcodeEntity1 = new Barcode();
        barcodeEntity1.setBarcodeId(1);
        Barcode barcodeEntity2 = new Barcode();
        barcodeEntity2.setBarcodeId(2);

        when(mapper.map(any(BarcodeResponse.class), eq(Barcode.class))).thenReturn(barcodeEntity1, barcodeEntity2);
        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcodeEntity1, barcodeEntity2);

        // Act
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(productId, categoryId, unit, productCode);

        // Assert
        assertEquals(2, barcodeResponses.size());
        assertEquals(BarcodeTypeEnum.URUN, barcodeResponses.get(0).getBarcodeType());
        assertEquals(BarcodeTypeEnum.TERAZI, barcodeResponses.get(1).getBarcodeType());
        assertEquals(productCode, barcodeResponses.get(1).getBarcode().substring(0, 5));

        verify(barcodeRepository, times(2)).save(any(Barcode.class));
        verify(mapper, times(2)).map(any(BarcodeResponse.class), eq(Barcode.class));
    }

    @Test
    void testGenerateAndSaveBarcodesForCategoryFishWithUnit() {
        // Arrange
        int productId = 1;
        int categoryId = 5;
        String unit = "UNIT";
        String productCode = "12345";

        Barcode barcodeEntity1 = new Barcode();
        barcodeEntity1.setBarcodeId(1);

        when(mapper.map(any(BarcodeResponse.class), eq(Barcode.class))).thenReturn(barcodeEntity1);
        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcodeEntity1);

        // Act
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(productId, categoryId, unit, productCode);

        // Assert
        assertEquals(1, barcodeResponses.size());
        assertEquals(BarcodeTypeEnum.KASA, barcodeResponses.get(0).getBarcodeType());

        verify(barcodeRepository, times(1)).save(any(Barcode.class));
        verify(mapper, times(1)).map(any(BarcodeResponse.class), eq(Barcode.class));
    }

    @Test
    void testGenerateAndSaveBarcodesForCategoryMeat() {
        // Arrange
        int productId = 1;
        int categoryId = 3;
        String unit = "KILOGRAM";
        String productCode = "12345";

        Barcode barcodeEntity1 = new Barcode();
        barcodeEntity1.setBarcodeId(1);

        when(mapper.map(any(BarcodeResponse.class), eq(Barcode.class))).thenReturn(barcodeEntity1);
        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcodeEntity1);

        // Act
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(productId, categoryId, unit, productCode);

        // Assert
        assertEquals(1, barcodeResponses.size());
        assertEquals(BarcodeTypeEnum.TERAZI, barcodeResponses.get(0).getBarcodeType());
        assertEquals(productCode, barcodeResponses.get(0).getBarcode().substring(0, 5));

        verify(barcodeRepository, times(1)).save(any(Barcode.class));
        verify(mapper, times(1)).map(any(BarcodeResponse.class), eq(Barcode.class));
    }

    @Test
    void testGenerateAndSaveBarcodesForDefaultCase() {
        // Arrange
        int productId = 1;
        int categoryId = 1; // Some other category
        String unit = "UNIT";
        String productCode = "12345";

        Barcode barcodeEntity1 = new Barcode();
        barcodeEntity1.setBarcodeId(1);

        when(mapper.map(any(BarcodeResponse.class), eq(Barcode.class))).thenReturn(barcodeEntity1);
        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcodeEntity1);

        // Act
        List<BarcodeResponse> barcodeResponses = barcodeService.generateAndSaveBarcodes(productId, categoryId, unit, productCode);

        // Assert
        assertEquals(1, barcodeResponses.size());
        assertEquals(BarcodeTypeEnum.URUN, barcodeResponses.get(0).getBarcodeType());

        verify(barcodeRepository, times(1)).save(any(Barcode.class));
        verify(mapper, times(1)).map(any(BarcodeResponse.class), eq(Barcode.class));
    }
}
