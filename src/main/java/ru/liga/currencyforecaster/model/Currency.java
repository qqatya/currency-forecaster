package ru.liga.currencyforecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * Класс для хранения данных из CSV-файлов со статистикой курсов валют
 */
@AllArgsConstructor
@Getter
@ToString
public class Currency {
    private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("E dd.MM.yyyy");
    private final int nominal;
    private final LocalDate date;
    private final BigDecimal rate;
    private final CurrencyTypeEnum currencyType;

    /**
     * Получение даты и курса
     *
     * @return Строка с отформатированными данными
     */
    public String getDayAndRate() {
        return SIMPLE_DATE_FORMATTER.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                + " - " + String.format("%.2f", rate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Currency currency = (Currency) obj;

        return Objects.equals(date, currency.date);
    }
}
