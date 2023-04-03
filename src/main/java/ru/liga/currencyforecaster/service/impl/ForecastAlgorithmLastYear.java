package ru.liga.currencyforecaster.service.impl;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForecastAlgorithmLastYear implements ForecastAlgorithm {
    @Override
    public List<Currency> predictRateForSomeDays(List<Currency> currencies,
                                                 LocalDate startDate,
                                                 int daysAmount) {
        List<Currency> tmpCurrencies = new ArrayList<>(currencies);
        List<Currency> ratesResult = new ArrayList<>();

        for (int i = 0; i < daysAmount; i++) {
            LocalDate rateDate = startDate.plusDays(i);
            Currency lastYearRete = predictRateForNextDay(tmpCurrencies, rateDate);
            Currency newRate = new Currency(lastYearRete.getNominal(),
                    rateDate,
                    lastYearRete.getRate(),
                    lastYearRete.getCurrencyType());

            ratesResult.add(newRate);
        }
        return ratesResult;
    }

    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        Currency temp = new Currency(1, date.minusYears(1), null, CurrencyType.DEF);
        int daysIncrement = 1;

        while (!currencies.contains(temp)) {
            temp = new Currency(1, date.minusYears(1).minusDays(daysIncrement),
                    null, CurrencyType.DEF);
            daysIncrement++;
        }
        return currencies.get(currencies.indexOf(temp));
    }
}