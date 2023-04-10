package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        int defaultValue = 1;
        Currency temp = new Currency(defaultValue, date.minusYears(1), null, CurrencyTypeEnum.DEF);
        int daysIncrement = 1;
        int yearsIncrement = 1;

        while (!currencies.contains(temp)) {
            temp = new Currency(defaultValue, date.minusYears(yearsIncrement).minusDays(daysIncrement),
                    null, CurrencyTypeEnum.DEF);
            daysIncrement++;
        }
        return currencies.get(currencies.indexOf(temp));
    }
}