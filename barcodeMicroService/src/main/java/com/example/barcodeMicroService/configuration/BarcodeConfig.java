package com.example.barcodeMicroService.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BarcodeConfig {
    @Value("${categoryservice.base.url}")
    private String addressBaseUrl;

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

    @Bean
    RestClient restClient() {
        return RestClient.builder()
                .baseUrl(addressBaseUrl)
                .build();
    }
}
