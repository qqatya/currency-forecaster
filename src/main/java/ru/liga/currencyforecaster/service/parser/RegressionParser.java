package ru.liga.currencyforecaster.service.parser;

import ru.liga.currencyforecaster.model.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegressionParser {

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
        return days;
    }

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
        return rates;
    }
}
