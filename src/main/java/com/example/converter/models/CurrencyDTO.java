package com.example.converter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDTO {
    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;
    @JsonProperty("Cur_Scale")
    private int curScale;
    @JsonProperty("Cur_Name")
    private String curName;
    @JsonProperty("Cur_OfficialRate")
    private double curOfficialRate;
}
