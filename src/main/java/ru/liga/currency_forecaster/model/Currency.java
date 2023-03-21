package ru.liga.currency_forecaster.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Сущность для хранения данных из CSV-файлов со статистикой курсов валют
 */
public class Currency {
    public static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("E dd.MM.yyyy");
    private final int nominal;
    private final LocalDate date;
    private final BigDecimal rate;
    private final String currencyType;

    public Currency(int nominal, LocalDate date, BigDecimal rate, String currencyType) {
        this.nominal = nominal;
        this.date = date;
        this.rate = rate;
        this.currencyType = currencyType;
    }

    public int getNominal() {
        return nominal;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    @Override
    public String toString() {

        return SIMPLE_DATE_FORMATTER.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                + " - " + String.format("%.2f", rate);
    }
}
