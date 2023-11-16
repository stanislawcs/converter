package com.example.converter.services;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;

import java.io.IOException;
import java.time.LocalDate;

public interface CurrencyService {
    Currency makeRequest(String abbreviation)throws IOException;

    Currency makeRequestWithDate(String abbreviation, LocalDate date)throws IOException;

    Response convert(Currency firstCurrency, Currency secondCurrency);
}
