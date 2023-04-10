package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.controller.RegressionParsingController;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.factory.ControllerFactory;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;
import ru.liga.currencyforecaster.utils.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.currencyforecaster.enums.MessageEnum.EMPTY_LIST;

@Slf4j
public class ForecastAlgorithmFromInternet implements ForecastAlgorithm {
    /**
     * Индекс объекта Currency, созданного из самой новой записи в csv-файле
     */
    private static final int NEWEST_CURRENCY_INDEX = 0;

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
            Currency currency = predictRateForNextDay(tmpCurrencies, startDate.plusDays(i));

            ratesResult.add(currency);
            tmpCurrencies.add(0, currency);
        }
        log.debug("Built linear regression rates");
        return ratesResult;
    }

    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        int nominal = currencies.get(NEWEST_CURRENCY_INDEX).getNominal();
        CurrencyTypeEnum currencyTypeEnum = currencies.get(NEWEST_CURRENCY_INDEX).getCurrencyType();
        RegressionParsingController regressionParsingController = ControllerFactory.getRegressionParsingController();
        LinearRegression regression = new LinearRegression(regressionParsingController.parseDays(currencies),
                regressionParsingController.parseRates(currencies));
        BigDecimal nextDayRate = BigDecimal.valueOf(regression.predict(date.getDayOfYear()));

        return new Currency(nominal, date, nextDayRate, currencyTypeEnum);
    }

}