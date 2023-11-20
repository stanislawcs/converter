package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.example.converter.models.cbrf.ExchangeRate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service(ServiceType.cbrf)
@Slf4j
public class CurrencyServiceCBRF implements CurrencyService {

    @Value("${api.cbrf.url}")
    private String url;

    @Value("${api.cbrf.onDate}")
    private String onDate;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    public CurrencyServiceCBRF(RestTemplate restTemplate, XmlMapper xmlMapper) {
        this.restTemplate = restTemplate;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Currency makeRequest(String abbreviation) throws IOException {
        log.info("CBRF");
        return getCurrencyEntity(url, abbreviation);
    }

    @Override
    public Currency makeRequestWithDate(String abbreviation, LocalDate date) throws IOException {
        StringBuilder urlWithDate = new StringBuilder(url).append(onDate).append(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return getCurrencyEntity(urlWithDate.toString(), abbreviation);
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        List<Currency> currencies = xmlMapper.readValue(responseEntity.getBody(), ExchangeRate.class).getValute();

        System.out.println(responseEntity);
        System.out.println(currencies.toString());

        for (Currency currency : currencies) {
            if (currency.getCharCode().equals(abbreviation)) {
                return currency;
            }
        }
        return new Currency(100, "Российский рубль", "100");
    }

}
