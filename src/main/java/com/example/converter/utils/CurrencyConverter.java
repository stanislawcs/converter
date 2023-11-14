package com.example.converter.utils;

import com.example.converter.dto.CurrencyDTO;
import com.example.converter.models.Currency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CurrencyConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CurrencyDTO convertToDTO(Currency currency){
        return modelMapper.map(currency,CurrencyDTO.class);
    }

    public Currency convertToCurrency(CurrencyDTO currencyDTO){
        return modelMapper.map(currencyDTO,Currency.class);
    }
}
