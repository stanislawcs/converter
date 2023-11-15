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

    @GetMapping("/get-rate")
    public ResponseEntity<Response> getRate(@RequestParam("first-currency") String firstCurrency,
                                                @RequestParam("second-currency")String secondCurrency) throws IOException {

        CurrencyDTO firstCurrencyDTO = currencyService.makeRequest(firstCurrency);
        CurrencyDTO secondCurrencyDTO = currencyService.makeRequest(secondCurrency);

        Response response = currencyService.convert(firstCurrencyDTO,secondCurrencyDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-rate-with-date")
    public ResponseEntity<Response> getRateWithDate(@RequestParam("first-currency") String firstCurrency,
                                                    @RequestParam("second-currency")String secondCurrency,
                                                    @RequestParam(value = "date",required = false)
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws IOException {

        CurrencyDTO firstCurrencyDTO = currencyService.makeRequestWithDate(firstCurrency,date);
        CurrencyDTO secondCurrencyDTO = currencyService.makeRequestWithDate(secondCurrency,date);

        Response response = currencyService.convert(firstCurrencyDTO,secondCurrencyDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
