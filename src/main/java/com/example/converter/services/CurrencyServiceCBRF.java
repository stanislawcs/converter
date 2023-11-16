package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.example.converter.models.cbrf.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@Service
@Profile("dev")
public class CurrencyServiceCBRF implements CurrencyService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public CurrencyServiceCBRF(RestTemplate restTemplate, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Currency makeRequest(String abbreviation) throws IOException {
        String url = "https://www.cbr-xml-daily.ru/daily_json.js";
        String response = restTemplate.getForObject(url, String.class);
        ExchangeRate exchangeRate = objectMapper.readValue(response,ExchangeRate.class);
        if(abbreviation.equals("RUB"))
            return new Currency(100,"Российский рубль",100);
        return exchangeRate.getCurrencyMap().get(abbreviation);
    }

    @Override
    public Currency makeRequestWithDate(String abbreviation, LocalDate date) throws IOException {
        return null;
    }

    @Override
    public Response convert(Currency firstCurrency, Currency secondCurrency) {
        Response response = makeResponse(firstCurrency, secondCurrency);
        response.setOfficialRate(firstCurrency.getValue() / secondCurrency.getValue() * secondCurrency.getNominal());
        return response;
    }

    private Response makeResponse(Currency firstCurrencyDTO, Currency secondCurrencyDTO){
        Response response = new Response();
        response.setFirstCurrencyName(firstCurrencyDTO.getName());
        response.setSecondCurrencyName(secondCurrencyDTO.getName());
        response.setCurScale(firstCurrencyDTO.getNominal());
        return response;
    }


}
