package com.example.converter.controllers;

import com.example.converter.models.Currency;
import com.example.converter.models.Response;
import com.example.converter.services.CurrencyService;
import com.example.converter.services.ServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("converter")
public class ConverterController {

    private final ServiceContext context;
    private final Map<String, CurrencyService> currencyServiceMap;

    @Autowired
    public ConverterController(ServiceContext context, Map<String, CurrencyService> currencyServiceMap) {
        this.context = context;
        this.currencyServiceMap = currencyServiceMap;
    }

    @GetMapping("/{type}/get-rate")
    public ResponseEntity<Response> getRate(@PathVariable("type") String type, @RequestParam("first-currency") String firstCurrency,
                                            @RequestParam("second-currency") String secondCurrency) throws IOException {

        CurrencyService currencyService = choiceTypeOfCurrencyService(type);
        context.setCurrencyService(currencyService);
        Response response = context.executeCurrencyService(firstCurrency, secondCurrency);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{type}/get-rate-with-date")
    public ResponseEntity<Response> getRateWithDate(@PathVariable("type") String type,@RequestParam("first-currency") String firstCurrency,
                                                    @RequestParam("second-currency") String secondCurrency,
                                                    @RequestParam(value = "date", required = false)
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws IOException {

        CurrencyService currencyService = choiceTypeOfCurrencyService(type);
        context.setCurrencyService(currencyService);
        Currency firstCurrencyModel = currencyService.makeRequestWithDate(firstCurrency, date);
        Currency secondCurrencyModel = currencyService.makeRequestWithDate(secondCurrency, date);

        Response response = currencyService.convert(firstCurrencyModel, secondCurrencyModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private CurrencyService choiceTypeOfCurrencyService(String type){

        if(type.equals("cbrf")) {
            return currencyServiceMap.get("cbrf");
        }
        else if(type.equals("nbrb")){
            return currencyServiceMap.get("nbrb");
        }

        else throw new RuntimeException();
    }

}
