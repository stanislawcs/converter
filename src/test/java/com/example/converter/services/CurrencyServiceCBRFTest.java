package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


@ExtendWith(MockitoExtension.class)
class CurrencyServiceCBRFTest {

    @InjectMocks
    private CurrencyServiceCBRF currencyServiceCBRF;

    @Mock
    RestTemplate restTemplate;

    @Mock
    XmlMapper xmlMapper;

    private String russianRubel;
    private String usDollar;

    @BeforeEach
    public void init() {
        russianRubel = "RUB";
        usDollar = "USD";
    }

    @Test
    void testConvert() {
        Currency firstCurrency = new Currency(100, "Российский рубль", "100");
        Currency secondCurrency = new Currency(1, "Белорусский рубль", "28,7125");

        Response response = currencyServiceCBRF.convert(firstCurrency, secondCurrency);

        Assertions.assertEquals("Российский рубль", response.getFirstCurrencyName());
        Assertions.assertEquals("Белорусский рубль", response.getSecondCurrencyName());

        Assertions.assertEquals(100, response.getCurScale());
        Assertions.assertEquals(3.48, response.getOfficialRate(), 1e-2);
    }
}