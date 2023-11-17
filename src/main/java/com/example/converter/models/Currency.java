package com.example.converter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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

    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String id;

    @JacksonXmlProperty(isAttribute = true, localName = "NumCode")
    private String numCode;

    @JacksonXmlProperty(isAttribute = true, localName = "CharCode")
    private String charCode;

    @JacksonXmlProperty(isAttribute = true, localName = "Nominal")
    private int nominal;

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "Value")
    private String value;

    @JacksonXmlProperty(isAttribute = true, localName = "VunitRate")
    private String vunitRate;

    public Currency(int nominal, String name, String value) {
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public Currency(String curName, int curScale, double curOfficialRate) {
        this.curScale = curScale;
        this.curName = curName;
        this.curOfficialRate = curOfficialRate;
    }

}
