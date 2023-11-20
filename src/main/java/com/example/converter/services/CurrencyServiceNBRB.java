package com.example.converter.services;

import com.example.converter.exceptions.AbbreviationIsNullException;
import com.example.converter.exceptions.ResponseIsNullException;
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

        checkCurrencyAbbreviationOnNull(abbreviation);

        if ("BYN".equals(abbreviation)) {
            return getDefaultCurrency();
        }

        String response = restTemplate.getForObject(url + abbreviation + mode, String.class);
        validateResponse(response);
        return objectMapper.readValue(response, Currency.class);
    }

    public Currency makeRequestWithDate(String abbreviation, LocalDate date) throws IOException {

        checkCurrencyAbbreviationOnNull(abbreviation);

        if ("BYN".equals(abbreviation)) {
            return getDefaultCurrency();
        }

        String response = restTemplate.getForObject(url + abbreviation + mode + "&" + onDate + date, String.class);

        validateResponse(response);
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

    private Currency getDefaultCurrency() {
        return new Currency("Белорусский рубль", 1, 1);
    }

    private void validateResponse(String response) {
        if (response == null)
            throw new ResponseIsNullException("Response from RestTemplate is null");
    }

    private void checkCurrencyAbbreviationOnNull(String abbreviation){
        if(abbreviation == null)
            throw new AbbreviationIsNullException("Currency abbreviation is null");
    }
}
