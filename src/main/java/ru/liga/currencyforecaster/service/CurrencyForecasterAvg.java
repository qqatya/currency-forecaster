package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyForecasterAvg {
    /**
     * Расчет прогноза на следующий день
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @return Результат прогноза
     */
    private static Currency predictRateForNextDay(List<Currency> currencies) {
        BigDecimal rateSum = new BigDecimal(0);
        BigDecimal daysAmount = new BigDecimal(currencies.size());
        int nominal = currencies.get(0).getNominal();
        String currencyType = currencies.get(0).getCurrencyType();

        for (Currency currency : currencies) {
            rateSum = rateSum.add(currency.getRate());
        }
        LocalDate date = LocalDate.now().plusDays(1);
        BigDecimal nextDayRate = rateSum.divide(daysAmount, RoundingMode.HALF_UP);

        return new Currency(nominal, date, nextDayRate, currencyType);
    }

    /**
     * Расчет прогноза на N дней
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param daysAmount Количество дней, на которые нужно рассчитать курс
     * @return Результат прогноза
     */
    public static List<Currency> predictRateForSomeDays(List<Currency> currencies, int daysAmount) {
        List<Currency> tmpCurrencies = new ArrayList<>(currencies);
        List<Currency> ratesResult = new ArrayList<>();

        for (int i = 1; i <= daysAmount; i++) {
            Currency currency = predictRateForNextDay(tmpCurrencies);

            ratesResult.add(currency);
            tmpCurrencies.remove(0);
            tmpCurrencies.add(currency);
        }
        return ratesResult;
    }
}
