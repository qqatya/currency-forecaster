package ru.liga.currencyforecaster.service.impl;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;
import ru.liga.currencyforecaster.service.parser.RegressionParser;
import ru.liga.currencyforecaster.utils.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForecastAlgorithmFromInternet implements ForecastAlgorithm {
    /**
     * Расчет прогноза на N дней
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param daysAmount Количество дней, на которые нужно рассчитать курс
     * @return Результат прогноза
     */
    @Override
    public List<Currency> predictRateForSomeDays(List<Currency> currencies,
                                                 LocalDate startDate,
                                                 int daysAmount) {
        List<Currency> tmpCurrencies = new ArrayList<>(currencies);
        List<Currency> ratesResult = new ArrayList<>();

        for (int i = 0; i < daysAmount; i++) {
            Currency currency = predictRateForNextDay(tmpCurrencies, startDate.plusDays(i));

            ratesResult.add(currency);
            tmpCurrencies.add(0, currency);
        }
        return ratesResult;
    }

    /**
     * Расчет прогноза на следующий день
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @return Результат прогноза
     */
    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        int nominal = currencies.get(0).getNominal();
        CurrencyType currencyType = currencies.get(0).getCurrencyType();
        LinearRegression regression = new LinearRegression(RegressionParser.parseDays(currencies),
                RegressionParser.parseRates(currencies));
        BigDecimal nextDayRate = BigDecimal.valueOf(regression.predict(date.getDayOfYear()));

        return new Currency(nominal, date, nextDayRate, currencyType);
    }

}