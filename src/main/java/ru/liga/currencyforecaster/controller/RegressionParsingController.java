package ru.liga.currencyforecaster.controller;

import ru.liga.currencyforecaster.model.Currency;

import java.util.List;

public interface RegressionParsingController {
    /**
     * Парсинг дней (x)
     *
     * @param currencies Список курсов валют
     * @return Список дней
     */
    List<Double> parseDays(List<Currency> currencies);

    /**
     * Парсинг курсов (y)
     *
     * @param currencies Список объектов, содержащих курсы валют
     * @return Список курсов валют
     */
    List<Double> parseRates(List<Currency> currencies);
}
