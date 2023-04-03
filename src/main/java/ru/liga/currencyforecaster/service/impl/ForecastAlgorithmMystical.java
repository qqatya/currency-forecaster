package ru.liga.currencyforecaster.service.impl;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForecastAlgorithmMystical implements ForecastAlgorithm {
    /**
     * Расчет прогноза на N дней
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param startDate  Дата, с которой начинается расчет
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
            LocalDate rateDate = startDate.plusDays(i);
            if (!ifDateExists(tmpCurrencies, rateDate)) {
                continue;
            }
            Currency lastYearRete = predictRateForNextDay(tmpCurrencies, rateDate);
            Currency newRate = new Currency(lastYearRete.getNominal(),
                    rateDate,
                    lastYearRete.getRate(),
                    lastYearRete.getCurrencyType());

            ratesResult.add(newRate);

        }
        return ratesResult;
    }

    /**
     * Поиск рандомного курса за прошлые календарные дни
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param date       Дата, по которой ведется расчет календарных дней
     * @return Результат поиска
     */
    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        Currency temp;
        Random random = new Random();

        do {
            int yearsAmount = random.nextInt(findYearsAmount(currencies));

            temp = new Currency(1, date.minusYears(yearsAmount),
                    null, CurrencyType.DEF);
        } while (!currencies.contains(temp));
        return currencies.get(currencies.indexOf(temp));
    }

    /**
     * Поиск интервала лет по считанным из файла значениям
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @return Интервал лет
     */
    private int findYearsAmount(List<Currency> currencies) {
        int firstYear = currencies.get(0).getDate().getYear();
        int lastYear = currencies.get(currencies.size() - 1).getDate().getYear();

        return firstYear - lastYear;
    }

    private boolean ifDateExists(List<Currency> currencies, LocalDate date) {
        for (Currency currency : currencies) {
            if (date.getMonth().equals(currency.getDate().getMonth())
                    && date.getDayOfMonth() == currency.getDate().getDayOfMonth()) {
                return true;
            }
        }
        return false;
    }
}
