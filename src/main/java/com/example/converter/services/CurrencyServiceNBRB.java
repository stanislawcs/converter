package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Service
@Profile("nbrb")
public class CurrencyServiceNBRB implements CurrencyService {

    @Value("${api.url}")
    private String url;

    @Value("${api.mode}")
    private String mode;

    @Value("${api.onDate}")
    private String onDate;


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CurrencyServiceNBRB(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Currency makeRequest(String abbreviation) throws IOException {
        log.info("NBRB - profile");
        StringBuilder request = new StringBuilder(url).append(abbreviation).append(mode);
        if(abbreviation.equals("BYN")){
            return new Currency("Белорусский рубль",1,1);
        }
        String response = restTemplate.getForObject(request.toString(), String.class);
        return objectMapper.readValue(response, Currency.class);
    }

    public Currency makeRequestWithDate(String abbreviation, LocalDate date) throws IOException {
        StringBuilder request = new StringBuilder(url).append(abbreviation).append(mode).append("&").append(onDate).append(date);

        if(abbreviation.equals("BYN")){
            return new Currency("Белорусский рубль",1,1);
        }

        String response = restTemplate.getForObject(request.toString(), String.class);
        return objectMapper.readValue(response, Currency.class);
    }

    @Override
    public Response convert(Currency firstCurrency, Currency secondCurrency) {
        Response response = makeResponse(firstCurrency, secondCurrency);
        response.setOfficialRate(firstCurrency.getCurOfficialRate() / secondCurrency.getCurOfficialRate() * secondCurrency.getCurScale());
        return response;
    }


    private Response makeResponse(Currency firstCurrencyDTO, Currency secondCurrencyDTO) {
        Response response = new Response();
        response.setFirstCurrencyName(firstCurrencyDTO.getCurName());
        response.setSecondCurrencyName(secondCurrencyDTO.getCurName());
        response.setCurScale(firstCurrencyDTO.getCurScale());

        return response;
    }
}
