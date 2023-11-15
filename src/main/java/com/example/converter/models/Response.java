package com.example.converter.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private String firstCurrencyName;
    private int curScale;
    private String secondCurrencyName;
    private double officialRate;

}
