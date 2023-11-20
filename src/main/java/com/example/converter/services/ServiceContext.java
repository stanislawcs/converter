package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ServiceContext {

    private CurrencyService currencyService;

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public Response executeCurrencyService(String firstCurrencyAbbreviation, String secondCurrencyAbbreviation) throws IOException {
        Currency firstCurrency = currencyService.makeRequest(firstCurrencyAbbreviation);
        Currency secondCurrency = currencyService.makeRequest(secondCurrencyAbbreviation);
        return currencyService.convert(firstCurrency, secondCurrency);
    }

}
