package com.example.converter.controller;

import com.example.converter.dto.CurrencyDTO;
import com.example.converter.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("converter")
public class ConverterController {

    private final CurrencyService currencyService;

    @Autowired
    public ConverterController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/get")
    public ResponseEntity<CurrencyDTO> getCurrency(@RequestParam("firstCurrency") String firstCurrency,@RequestParam("secondCurrency")String secondCurrency) throws IOException {
        CurrencyDTO firstCurrencyDTO = currencyService.makeRequest(firstCurrency);
        CurrencyDTO secondCurrencyDTO = currencyService.makeRequest(secondCurrency);

        return new ResponseEntity<>(secondCurrencyDTO, HttpStatus.OK);
    }

}
