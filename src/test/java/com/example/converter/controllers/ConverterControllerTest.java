package com.example.converter.controllers;

import com.example.converter.services.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
class ConverterControllerTest {
    @LocalServerPort
    private int port;

    @Mock
    private TestRestTemplate restTemplate;

    @Autowired
    @InjectMocks
    private ConverterController controller;

    @MockBean
    private CurrencyService service;

    @Test
    void testGetRateCBRF() {
        String response = restTemplate.getForObject("http://localhost:8080/converter/get-rate?first-currency=USD&second-currency=RUB", String.class);
        assertThat(response).contains("{\"firstCurrencyName\":\"Доллар США\",\"curScale\":1,\"secondCurrencyName\":\"Российский рубль\",\"officialRate\":88.9466}");
    }

    @Test
    void testGetRateWithDateCBRF(){
        String response = restTemplate.getForObject("http://localhost:8080/converter/get-rate-with-date?first-currency=USD&second-currency=RUB&date=2023-11-11",String.class);
        assertThat(response).contains("{\"firstCurrencyName\":\"Доллар США\",\"curScale\":1,\"secondCurrencyName\":\"Российский рубль\",\"officialRate\":92.0535}");
    }

    @Test
    void testGetRateWithDateNBRB(){
        String response = restTemplate.getForObject("http://localhost:8080/converter/get-rate?first-currency=USD&second-currency=BYN",String.class);
        assertThat(response).contains("{\"firstCurrencyName\":\"Доллар США\",\"curScale\":1,\"secondCurrencyName\":\"Белорусский рубль\",\"officialRate\":3.1033}");

    }

    @Test
    void testGetRateWitDateNBRB(){
        String response = restTemplate.getForObject("http://localhost:8080/converter/get-rate-with-date?first-currency=USD&second-currency=BYN&date=2023-11-11",String.class);
        assertThat(response).contains("{\"firstCurrencyName\":\"Доллар США\",\"curScale\":1,\"secondCurrencyName\":\"Белорусский рубль\",\"officialRate\":3.1764}");

    }
}
