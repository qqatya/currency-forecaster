package ru.liga.currencyforecaster.utils;

import lombok.RequiredArgsConstructor;
import ru.liga.currencyforecaster.model.Currency;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
public class OutputFormatter {
    private final SimpleDateFormat formatter;

    /**
     * Вывод отформатированных даты и курса
     *
     * @return Строка с отформатированными данными
     */
    public String printDayAndRate(Currency currency) {
        return formatter.format(Date.from(currency.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                + " - " + String.format("%.2f", currency.getRate());
    }
}
