package com.example.converter.controllers;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.example.converter.services.CurrencyService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("converter")
public class ConverterController {

    private final CurrencyService currencyService;

    @Autowired
    public ConverterController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/get-rate")
    public ResponseEntity<Response> getRate(@RequestParam("first-currency") @NotBlank(message = "Should not be empty")
                                            @Size(min = 3, max = 3,
                                                    message = "According ISO 4217 standard currency abbreviation length should be 3")
                                                    String firstCurrency,
                                            @RequestParam("second-currency") @NotBlank(message = "Should not be empty")
                                            @Size(min = 3, max = 3,
                                                    message = "According ISO 4217 standard currency abbreviation length should be 3")
                                                    String secondCurrency) throws IOException {

        Currency firstCurrencyModel = currencyService.makeRequest(firstCurrency);
        Currency secondCurrencyModel = currencyService.makeRequest(secondCurrency);

        Response response = currencyService.convert(firstCurrencyModel, secondCurrencyModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-rate-on-date")
    public ResponseEntity<Response> getRateWithDate(@RequestParam("first-currency") @NotBlank(message = "Should not be empty")
                                                    @Size(min = 3, max = 3,
                                                            message = "According ISO 4217 standard currency abbreviation length should be 3")
                                                            String firstCurrency,
                                                    @RequestParam("second-currency") @NotBlank(message = "Should not be empty")
                                                    @Size(min = 3, max = 3,
                                                            message = "According ISO 4217 standard currency abbreviation length should be 3")
                                                            String secondCurrency,
                                                    @RequestParam("date")
                                                    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format should be: yyyy-MM-dd") String date) throws IOException {

        LocalDate localDate = LocalDate.parse(date);

        Currency firstCurrencyModel = currencyService.makeRequestWithDate(firstCurrency, localDate);
        Currency secondCurrencyModel = currencyService.makeRequestWithDate(secondCurrency, localDate);

        Response response = currencyService.convert(firstCurrencyModel, secondCurrencyModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
