package ru.liga.currencyforecaster.factory;

import ru.liga.currencyforecaster.mapper.CurrencyMapper;
import ru.liga.currencyforecaster.mapper.impl.CurrencyMapperImpl;

public class CurrencyMapperFactory {
    public static CurrencyMapper getCurrencyMapper() {
        return new CurrencyMapperImpl();
    }
}
