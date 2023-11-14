package com.example.converter.services;

import com.example.converter.dto.CurrencyDTO;
import com.example.converter.models.Currency;
import com.example.converter.utils.CurrencyConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CurrencyService {

    private final CurrencyConverter currencyConverter;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CurrencyService(CurrencyConverter currencyConverter, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.currencyConverter = currencyConverter;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public CurrencyDTO makeRequest(String abbreviation) throws IOException {
        String url = "https://api.nbrb.by/exrates/rates/" + abbreviation + "?parammode=2";
        String response = restTemplate.getForObject(url, String.class);
        Currency currency = objectMapper.readValue(response, Currency.class);
        return currencyConverter.convertToDTO(currency);
    }

}
