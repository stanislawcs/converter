package com.example.converter.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private String firstCurrencyName;
    private double officialRate;
    private String secondCurrencyName;
    private int curScale;

}
