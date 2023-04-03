package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.Currency;

import java.time.LocalDate;
import java.util.List;

public interface ForecastAlgorithm {
    /**
     * Расчет прогноза на N дней
     *
     * @param currencies Список сущностей, по которым ведется расчет
     * @param startDate  Дата начала прогноза
     * @param daysAmount Количество дней, на которые нужно рассчитать курс
     * @return Результат прогноза
     */
    List<Currency> predictRateForSomeDays(List<Currency> currencies, LocalDate startDate, int daysAmount);
}
