package com.example.barcodeMicroService;

import com.example.barcodeMicroService.controller.BarcodeController;
import com.example.barcodeMicroService.model.BarcodeRequest;
import com.example.barcodeMicroService.model.BarcodeResponse;
import com.example.barcodeMicroService.service.BarcodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BarcodeController.class)
class BarcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarcodeService barcodeService;

    @Autowired
    private ObjectMapper objectMapper;

    private BarcodeRequest barcodeRequest;
    private List<BarcodeResponse> barcodeResponses;

    @BeforeEach
    void setUp() {
        barcodeRequest = new BarcodeRequest();
        barcodeRequest.setProductId(4);
        barcodeRequest.setCategoryId(2);
        barcodeRequest.setUnit("KILOGRAM");
        barcodeRequest.setProductCode("MEHKU");

        BarcodeResponse barcodeResponse1 = new BarcodeResponse();
        barcodeResponse1.setBarcodeId(32);
        barcodeResponse1.setBarcode("114460740");
        barcodeResponse1.setBarcodeType(BarcodeTypeEnum.URUN); // Assuming TYPE1 is a valid enum value
        barcodeResponse1.setProductId(1);

        BarcodeResponse barcodeResponse2 = new BarcodeResponse();
        barcodeResponse2.setBarcodeId(33);
        barcodeResponse2.setBarcode("5760");
        barcodeResponse2.setBarcodeType(BarcodeTypeEnum.KASA); // Assuming TYPE2 is a valid enum value
        barcodeResponse2.setProductId(1);

        barcodeResponses = Arrays.asList(barcodeResponse1, barcodeResponse2);
    }

    @Test
    void testGenerateBarcodes() throws Exception {
        // Arrange
        when(barcodeService.generateAndSaveBarcodes(4, 2, "KILOGRAM", "MEHKU"))
                .thenReturn(barcodeResponses);

        // Act & Assert
        mockMvc.perform(post("/api/barcode/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(barcodeRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(barcodeResponses)));

        // Verify that the service method was called with the correct arguments
        verify(barcodeService).generateAndSaveBarcodes(4, 2, "KILOGRAM", "MEHKU");
    }
}
