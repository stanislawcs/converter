package com.example.converter.services;

import com.example.converter.models.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceNBRBTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;


    @InjectMocks
    private CurrencyServiceNBRB currencyServiceNBRB;

    @Test
    void makeRequestTest() throws IOException {
        Currency expectedCurrency = new Currency("Российский рубль",100,3.4871);
        Currency resultCurrency = currencyServiceNBRB.makeRequest("RUB");

        Assertions.assertEquals(expectedCurrency,resultCurrency);
    }
}
