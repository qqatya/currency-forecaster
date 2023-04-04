package ru.liga.currencyforecaster.service.parser;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.model.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-парсер курсов валют для расчета по алгоритму линейной регрессии
 */
@Slf4j
public class RegressionParser {
    /**
     * Парсинг дней (x)
     *
     * @param currencies Список курсов валют
     * @return Список дней
     */
    public static List<Double> parseDays(List<Currency> currencies) {
        List<Double> days = new ArrayList<>();
        LocalDate dateToCompare = currencies.get(0).getDate().minusMonths(1);
        LocalDate latestDate = currencies.get(0).getDate();

        for (Currency currency : currencies) {
            LocalDate currentDate = currency.getDate();

            if (currentDate.isBefore(dateToCompare)) {
                break;
            } else if (latestDate.getYear() > currentDate.getYear()) {
                int dayNumber = currentDate.isLeapYear() ? currentDate.getDayOfYear() - 366
                        : currentDate.getDayOfYear() - 365;
                days.add((double) dayNumber);
            } else if (currentDate.isAfter(dateToCompare) || currentDate.equals(dateToCompare)) {
                days.add((double) currentDate.getDayOfYear());
            }
        }
        log.debug("Successfully parsed {} days for linear regression", days.size());
        return days;
    }

    /**
     * Парсинг курсов (y)
     *
     * @param currencies Список объектов, содержащиъ курсы валют
     * @return Список курсов валют
     */
    public static List<Double> parseRates(List<Currency> currencies) {
        List<Double> rates = new ArrayList<>();
        LocalDate dateToCompare = currencies.get(0).getDate().minusMonths(1);

        for (Currency currency : currencies) {
            LocalDate currentDate = currency.getDate();

            if (currentDate.isAfter(dateToCompare) || currentDate.equals(dateToCompare)) {
                rates.add(currency.getRate().doubleValue());
            } else {
                break;
            }
        }
        log.debug("Successfully parsed {} rates for linear regression", rates.size());
        return rates;
    }
}
