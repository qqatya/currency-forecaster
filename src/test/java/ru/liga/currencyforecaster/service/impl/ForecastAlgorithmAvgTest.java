package ru.liga.currencyforecaster.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForecastAlgorithmAvgTest {
    private static ForecastAlgorithmAvg algorithm;
    private static List<Currency> currencies;

    @BeforeAll
    static void setUp() {
        algorithm = new ForecastAlgorithmAvg();
        currencies = new ArrayList<>();
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 1),
                new BigDecimal("10.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 2),
                new BigDecimal("20.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 3),
                new BigDecimal("30.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 4),
                new BigDecimal("30.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 5),
                new BigDecimal("20.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 6),
                new BigDecimal("10.00"),
                CurrencyTypeEnum.EUR));
        currencies.add(new Currency(1,
                LocalDate.of(2023, 4, 7),
                new BigDecimal("70.00"),
                CurrencyTypeEnum.EUR));
    }

    @Test
    void returnsRateForTomorrow() {
        List<Currency> result = algorithm.predictRate(currencies,
                LocalDate.of(2023, 4, 12),
                1);
        assertEquals(new BigDecimal("27.14"), result.get(0).getRate());
    }
}