package ru.liga.csv;

import ru.liga.currency.CurrencyDto;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 7;

    /**
     * Построчное чтение данных из файла для создания сущностей, по которым ведется прогноз
     * @param io Поток для чтения файла
     * @return Результат чтения файла
     */
    public static List<CurrencyDto> loadFromFile(InputStream io) {
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        Reader reader = new InputStreamReader(io);
        try (BufferedReader br = new BufferedReader(reader)) {
            br.readLine();
            for (int i = 0; i < RECORDS_AMOUNT; i++) {
                String str = br.readLine();

                currencyDtos.add(fromString(str));
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка при считывании файла");
        }
        return currencyDtos;
    }

    /**
     * Конвертация строки в объект CurrencyDto
     * @param value Строка, содержащая значения аргументов конструктора CurrencyDto
     * @return Объект CurrencyDto
     */
    private static CurrencyDto fromString(String value) {
        String[] split = value.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int nominal = Integer.parseInt(split[0]);
        LocalDate date = LocalDate.parse(split[1], formatter);
        double rate = Double.parseDouble(split[2].replace(',', '.'));
        String currencyName = split[3];

        return new CurrencyDto(nominal, date, rate, currencyName);
    }
}
