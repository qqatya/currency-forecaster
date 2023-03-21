package ru.liga.currency_forecaster.service;

import ru.liga.currency_forecaster.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyForecasterService {
    /**
     * Расчет прогноза на следующий день
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @return Спрогнозированный курс
     */
    private static BigDecimal predictRateForNextDay(List<Currency> currencies) {
        BigDecimal rateSum = new BigDecimal(0);
        BigDecimal daysAmount = new BigDecimal(currencies.size());

        for (Currency currency : currencies) {
            rateSum = rateSum.add(currency.getRate());
        }
        return rateSum.divide(daysAmount, RoundingMode.HALF_UP);
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
        int nominal = tmpCurrencies.get(0).getNominal();
        String currencyType = tmpCurrencies.get(0).getCurrencyType();

        for (int i = 1; i <= daysAmount; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            BigDecimal predictedRate = predictRateForNextDay(tmpCurrencies);
            Currency currency = new Currency(nominal, date, predictedRate, currencyType);

            ratesResult.add(currency);
            tmpCurrencies.remove(0);
            tmpCurrencies.add(currency);
        }
        return ratesResult;
    }
}
