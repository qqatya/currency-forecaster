package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.liga.currencyforecaster.enums.MessageEnum.EMPTY_LIST;

@Slf4j
public class ForecastAlgorithmLastYear implements ForecastAlgorithm {
    @Override
    public List<Currency> predictRate(List<Currency> currencies,
                                      LocalDate startDate,
                                      int daysAmount) {
        if (currencies.isEmpty()) {
            throw new EmptyObjectException(EMPTY_LIST.getMessage());
        }
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
        log.debug("Built last year rates");
        return ratesResult;
    }

    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        Map<LocalDate, Currency> tempCur = new HashMap<>();
        LocalDate lastYearDate = date.minusYears(1);
        int daysIncrement = 1;
        int yearsIncrement = 1;

        for (Currency currency : currencies) {
            tempCur.put(currency.getDate(), currency);
        }
        while (!tempCur.containsKey(lastYearDate)) {
            lastYearDate = date.minusYears(yearsIncrement).minusDays(daysIncrement);
            daysIncrement++;
        }
        return tempCur.get(lastYearDate);
    }
}