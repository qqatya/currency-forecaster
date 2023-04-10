package ru.liga.currencyforecaster.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.enums.CsvColumnsEnum;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.exception.ValidationException;
import ru.liga.currencyforecaster.mapper.CurrencyMapper;
import ru.liga.currencyforecaster.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.currencyforecaster.enums.DelimiterEnum.*;

@Slf4j
public class CurrencyMapperImpl implements CurrencyMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final int CSV_COLUMNS_NAMES_INDEX = 0;

    /**
     * Индекс значения для поля nominal при парсинге строки в объект Currency
     */
    private static int nominalIndex;

    /**
     * Индекс значения для поля date при парсинге строки в объект Currency
     */
    private static int dateIndex;

    /**
     * Индекс значения для поля rate при парсинге строки в объект Currency
     */
    private static int rateIndex;

    /**
     * Индекс значения для поля currencyType при парсинге строки в объект Currency
     */
    private static int currencyTypeIndex;

    @Override
    public List<Currency> parseFile(List<String> lines) {
        List<Currency> currencies = new ArrayList<>();
        String[] columnNames = lines.get(CSV_COLUMNS_NAMES_INDEX).split(SEMICOLON.getValue());

        for (int i = 0; i < columnNames.length; i++) {
            switch (CsvColumnsEnum.findByCommand(columnNames[i])) {
                case NOMINAL -> nominalIndex = i;
                case DATA -> dateIndex = i;
                case CURS -> rateIndex = i;
                case CDX -> currencyTypeIndex = i;
                case DEF -> throw new ValidationException("File structure is not valid");
            }
        }
        for (int i = 1; i < lines.size(); i++) {
            currencies.add(convertStringToCurrency(lines.get(i)));
        }
        log.debug("Successfully converted lines to currencies");
        return currencies;
    }

    /**
     * Конвертация строки в объект Currency
     *
     * @param value Строка, содержащая значения аргументов конструктора Currency
     * @return Объект Currency
     */
    private Currency convertStringToCurrency(String value) {
        String[] split = value.split(SEMICOLON.getValue());
        int nominal = Integer.parseInt(split[nominalIndex].replaceAll(WHITESPACES.getValue(), EMPTY_STR.getValue()));
        LocalDate date = LocalDate.parse(split[dateIndex], FORMATTER);
        BigDecimal rate = new BigDecimal(split[rateIndex].replace(COMMA.getValue(), DOT.getValue()));
        CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.findByCurrencyName(split[currencyTypeIndex]);

        return new Currency(nominal, date, rate, currencyTypeEnum);
    }
}
