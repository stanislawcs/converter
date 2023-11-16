package com.example.converter.models.cbrf;

import com.example.converter.models.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class ExchangeRate {
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("PreviousDate")
    private Date previousDate;
    @JsonProperty("PreviousURL")
    private String previousURL;
    @JsonProperty("Timestamp")
    private Date timestamp;

    @JsonProperty("Valute")
    private Map<String, Currency> currencyMap;
}
