package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.Currency;

import java.time.LocalDate;
import java.util.List;

public interface ForecastAlgorithm {
    public List<Currency> predictRateForSomeDays(List<Currency> currencies, LocalDate startDate, int daysAmount);
}
