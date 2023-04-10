package ru.liga.currencyforecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс для хранения данных из CSV-файлов со статистикой курсов валют
 */
@AllArgsConstructor
@Getter
@ToString
public class Currency {
    private final int nominal;
    private final LocalDate date;
    private final BigDecimal rate;
    private final CurrencyTypeEnum currencyType;
}
