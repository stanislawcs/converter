package com.example.converter.services;

import com.example.converter.models.CurrencyDTO;
import com.example.converter.models.Currency;
import com.example.converter.utils.CurrencyConverter;
import com.example.converter.models.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

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
        StringBuilder url = new StringBuilder("https://api.nbrb.by/exrates/rates/").append(abbreviation).append("?parammode=2");
        String response = restTemplate.getForObject(url.toString(), String.class);
        Currency currency = objectMapper.readValue(response, Currency.class);
        return currencyConverter.convertToDTO(currency);
    }

    public CurrencyDTO makeRequestWithDate(String abbreviation, LocalDate date) throws JsonProcessingException {
        StringBuilder url = new StringBuilder("https://api.nbrb.by/exrates/rates/").append(abbreviation).append("?parammode=2").append("&ondate=").append(date);
        String response = restTemplate.getForObject(url.toString(), String.class);
        Currency currency = objectMapper.readValue(response, Currency.class);
        return currencyConverter.convertToDTO(currency);
    }

    public Response convert(CurrencyDTO firstCurrencyDTO, CurrencyDTO secondCurrencyDTO) {
        Response response = makeResponse(firstCurrencyDTO, secondCurrencyDTO);
        response.setOfficialRate(firstCurrencyDTO.getCurOfficialRate() / secondCurrencyDTO.getCurOfficialRate() * secondCurrencyDTO.getCurScale());
        return response;
    }

    private Response makeResponse(CurrencyDTO firstCurrencyDTO, CurrencyDTO secondCurrencyDTO) {
        Response response = new Response();
        response.setFirstCurrencyName(firstCurrencyDTO.getCurName());
        response.setSecondCurrencyName(secondCurrencyDTO.getCurName());
        response.setCurScale(firstCurrencyDTO.getCurScale());

        return response;
    }
}
