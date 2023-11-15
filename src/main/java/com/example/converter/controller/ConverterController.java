package com.example.converter.controller;

import com.example.converter.models.CurrencyDTO;
import com.example.converter.services.CurrencyService;
import com.example.converter.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("converter")
public class ConverterController {

    private final CurrencyService currencyService;

    @Autowired
    public ConverterController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getCurrency(@RequestParam("firstCurrency") String firstCurrency,
                                                @RequestParam("secondCurrency")String secondCurrency,
                                                @RequestParam(value = "date",required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws IOException {

        CurrencyDTO firstCurrencyDTO = currencyService.makeRequest(firstCurrency,date);
        CurrencyDTO secondCurrencyDTO = currencyService.makeRequest(secondCurrency,date);

        Response response = currencyService.convert(firstCurrencyDTO,secondCurrencyDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
