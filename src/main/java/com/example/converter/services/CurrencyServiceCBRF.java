package com.example.converter.services;

import com.example.converter.exceptions.AbbreviationIsNullException;
import com.example.converter.exceptions.ResponseBodyIsNullException;
import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.example.converter.models.cbrf.ExchangeRate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Profile("cbrf")
public class CurrencyServiceCBRF implements CurrencyService {

    @Value("${api.url}")
    private String url;

    @Value("${api.onDate}")
    private String onDate;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    public CurrencyServiceCBRF(RestTemplate restTemplate, XmlMapper xmlMapper) {
        this.restTemplate = restTemplate;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Currency makeRequest(String abbreviation) throws IOException {
        return getCurrencyEntity(url, abbreviation);
    }

    @Override
    public Currency makeRequestWithDate(String abbreviation, LocalDate date) throws IOException {
        return getCurrencyEntity(url + onDate + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), abbreviation);
    }

    @Override
    public Response convert(Currency firstCurrency, Currency secondCurrency) {
        Response response = makeResponse(firstCurrency, secondCurrency);
        double firstValue = Double.parseDouble(firstCurrency.getValue().replace(",", "."));
        double secondValue = Double.parseDouble(secondCurrency.getValue().replace(",", "."));
        response.setOfficialRate(firstValue / secondValue * secondCurrency.getNominal());
        return response;
    }

    private Response makeResponse(Currency firstCurrencyDTO, Currency secondCurrencyDTO) {
        Response response = new Response();
        response.setFirstCurrencyName(firstCurrencyDTO.getName());
        response.setSecondCurrencyName(secondCurrencyDTO.getName());
        response.setCurScale(firstCurrencyDTO.getNominal());
        return response;
    }

    private Currency getCurrencyEntity(String url, String abbreviation) throws IOException {
        checkAbbreviationOnNull(abbreviation);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        checkResponseEntityOnNull(responseEntity);

        List<Currency> currencies = xmlMapper.readValue(responseEntity.getBody(), ExchangeRate.class).getValute();

        for (Currency currency : currencies) {
            if (currency.getCharCode().equals(abbreviation)) {
                return currency;
            }
        }

        throw new RuntimeException();

        //return new Currency(100, "Российский рубль", "100");
    }

    private void checkAbbreviationOnNull(String abbreviation){
        if(abbreviation == null)
        throw new AbbreviationIsNullException("Currency abbreviation is null");
    }

    private void checkResponseEntityOnNull(ResponseEntity<?> responseEntity){
        if(responseEntity.getBody() == null)
            throw new ResponseBodyIsNullException("Response body is null");
    }

}
