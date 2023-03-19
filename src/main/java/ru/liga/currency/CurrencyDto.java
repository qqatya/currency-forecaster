package ru.liga.currency;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Сущность для хранения данных из CSV-файлов со статистикой курсов валют
 */
public class CurrencyDto {
    private int nominal;
    private LocalDate date;
    private double rate;
    private String currencyName;

    public CurrencyDto(int nominal, LocalDate date, double rate, String currencyName) {
        this.nominal = nominal;
        this.date = date;
        this.rate = rate;
        this.currencyName = currencyName;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("E dd.MM.yyyy");

        return formatter.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                + " - " + String.format("%.4f", rate);
    }
}
