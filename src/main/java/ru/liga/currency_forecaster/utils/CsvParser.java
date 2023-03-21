package ru.liga.currency_forecaster.utils;

import ru.liga.currency_forecaster.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 7;

    /**
     * Индекс значения для поля nominal при парсинге строки в объект Currency
     */
    private static final int NOMINAL_INDEX = 0;

    /**
     * Индекс значения для поля date при парсинге строки в объект Currency
     */
    private static final int DATE_INDEX = 1;

    /**
     * Индекс значения для поля rate при парсинге строки в объект Currency
     */
    private static final int RATE_INDEX = 2;

    /**
     * Индекс значения для поля currencyType при парсинге строки в объект Currency
     */
    private static final int CURRENCY_TYPE_INDEX = 3;

    /**
     * Парсинг строк, считанных из файла, для создания сущностей, по которым ведется прогноз
     *
     * @param lines Список строк, полученных при чтении файла
     * @return Результат парсинга
     */
    public static List<Currency> parseFile(List<String> lines) {
        List<Currency> currencies = new ArrayList<>();

        for (int i = 0; i < RECORDS_AMOUNT; i++) {
            currencies.add(fromString(lines.get(i)));
        }
        return currencies;
    }

    /**
     * Конвертация строки в объект Currency
     *
     * @param value Строка, содержащая значения аргументов конструктора Currency
     * @return Объект Currency
     */
    private static Currency fromString(String value) {
        String[] split = value.split(";");
        int nominal = Integer.parseInt(split[NOMINAL_INDEX]);
        LocalDate date = LocalDate.parse(split[DATE_INDEX], FORMATTER);
        BigDecimal rate = new BigDecimal(split[RATE_INDEX].replace(',', '.'));
        String currencyType = split[CURRENCY_TYPE_INDEX];

        return new Currency(nominal, date, rate, currencyType);
    }
}
