package ru.liga.currencyforecaster.service.impl;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForecastAlgorithmAvg implements ForecastAlgorithm {
    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 7;
    /**
     * Расчет прогноза на N дней
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param daysAmount Количество дней, на которые нужно рассчитать курс
     * @return Результат прогноза
     */
    //TODO Сделать функциональный интерфейс на метод predictRateForNextDay?
    // И один класс с predictRateForSomeDays, куда передаем интерфейс для вызова нужного метода
    // Если делать интерфейс, реализации положить в папку impl
    @Override
    public List<Currency> predictRateForSomeDays(List<Currency> currencies,
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
        return ratesResult;
    }

    /**
     * Расчет прогноза на следующий день
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @return Результат прогноза
     */
    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        BigDecimal rateSum = new BigDecimal(0);
        BigDecimal daysAmount = new BigDecimal(RECORDS_AMOUNT);
        int nominal = currencies.get(0).getNominal();
        CurrencyType currencyType = currencies.get(0).getCurrencyType();

        for (int i = 0; i < RECORDS_AMOUNT; i++) {
            rateSum = rateSum.add(currencies.get(i).getRate());
        }
        BigDecimal nextDayRate = rateSum.divide(daysAmount, RoundingMode.HALF_UP);

        return new Currency(nominal, date, nextDayRate, currencyType);
    }
}