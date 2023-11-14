package com.example.converter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Currency {
    @JsonProperty("Cur_ID")
    private int сurId;
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;
    @JsonProperty("Cur_Scale")
    private int curScale;
    @JsonProperty("Cur_Name")
    private String curName;
    @JsonProperty("Cur_OfficialRate")
    private double curOfficialRate;
}
