package ru.liga.currencyforecaster.controller.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.controller.RegressionParsingController;
import ru.liga.currencyforecaster.model.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Парсер курсов валют для расчета по алгоритму линейной регрессии
 */
@Slf4j
public class RegressionParsingControllerImpl implements RegressionParsingController {
    /**
     * Индекс объекта Currency, созданного из самой новой записи в csv-файле
     */
    private static final int NEWEST_CURRENCY_INDEX = 0;
    /**
     * Количество дней в високосном году
     */
    private static final int LEAP_YEAR_DAYS = 366;
    /**
     * Количество дней в не високосном году
     */
    private static final int NON_LEAP_YEAR_DAYS = 365;

    @Override
    public List<Double> parseDays(List<Currency> currencies) {
        List<Double> days = new ArrayList<>();
        LocalDate dateToCompare = currencies.get(NEWEST_CURRENCY_INDEX).getDate().minusMonths(1);
        LocalDate latestDate = currencies.get(NEWEST_CURRENCY_INDEX).getDate();

        for (Currency currency : currencies) {
            LocalDate currentDate = currency.getDate();

            if (currentDate.isBefore(dateToCompare)) {
                break;
            } else if (latestDate.getYear() > currentDate.getYear()) {
                int dayNumber = currentDate.isLeapYear() ? currentDate.getDayOfYear() - LEAP_YEAR_DAYS
                        : currentDate.getDayOfYear() - NON_LEAP_YEAR_DAYS;
                days.add((double) dayNumber);
            } else if (currentDate.isAfter(dateToCompare) || currentDate.equals(dateToCompare)) {
                days.add((double) currentDate.getDayOfYear());
            }
        }
        log.debug("Successfully parsed {} days for linear regression", days.size());
        return days;
    }

    @Override
    public List<Double> parseRates(List<Currency> currencies) {
        List<Double> rates = new ArrayList<>();
        LocalDate dateToCompare = currencies.get(NEWEST_CURRENCY_INDEX).getDate().minusMonths(1);

        for (Currency currency : currencies) {
            LocalDate currentDate = currency.getDate();

            if (currentDate.isBefore(dateToCompare)) {
                break;
            }
            rates.add(currency.getRate().doubleValue());
        }
        log.debug("Successfully parsed {} rates for linear regression", rates.size());
        return rates;
    }
}
