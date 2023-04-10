package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ForecastAlgorithmAvg implements ForecastAlgorithm {
    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 7;

    /**
     * Индекс объекта Currency, созданного из самой новой записи в csv-файле
     */
    private static final int NEWEST_CURRENCY_INDEX = 0;

    @Override
    public List<Currency> predictRate(List<Currency> currencies,
                                      LocalDate startDate,
                                      int daysAmount) {
        List<Currency> tmpCurrencies = new ArrayList<>(currencies);
        List<Currency> ratesResult = new ArrayList<>();

        for (int i = 0; i < daysAmount; i++) {
            Currency currency = predictRateForNextDay(tmpCurrencies, startDate.plusDays(i));

            ratesResult.add(currency);
            tmpCurrencies.remove(tmpCurrencies.size() - 1);
            tmpCurrencies.add(0, currency);
        }
        log.debug("Built avg rates: {}", ratesResult.size());
        return ratesResult;
    }

    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        BigDecimal rateSum = new BigDecimal(0);
        BigDecimal daysAmount = new BigDecimal(RECORDS_AMOUNT);
        int nominal = currencies.get(0).getNominal();
        CurrencyTypeEnum currencyTypeEnum = currencies.get(NEWEST_CURRENCY_INDEX).getCurrencyType();

        for (int i = 0; i < RECORDS_AMOUNT; i++) {
            rateSum = rateSum.add(currencies.get(i).getRate());
        }
        BigDecimal nextDayRate = rateSum.divide(daysAmount, RoundingMode.HALF_UP);

        return new Currency(nominal, date, nextDayRate, currencyTypeEnum);
    }
}