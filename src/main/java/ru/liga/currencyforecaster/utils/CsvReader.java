package ru.liga.currencyforecaster.utils;

import ru.liga.currencyforecaster.CurrencyForecasterApp;
import ru.liga.currencyforecaster.model.type.CurrencyType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.currencyforecaster.model.type.ConsoleMessage.IO_EXCEPTION_MESSAGE;

public class CsvReader {
    /**
     * Поиск файла со статистикой по типу валюты
     *
     * @param currencyType Тип валюты
     * @return Потоковое представление файла
     */
    public static InputStream loadFileByPath(String path) {
        return CurrencyForecasterApp.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * Построчное чтение определенного количества строк из потока
     *
     * @param io          Поток для чтения файла
     * @param linesAmount Количество строк, которое нужно считать
     * @return Список считанных строк
     */
    public static List<String> readFromFile(InputStream io, int linesAmount) {
        List<String> lines = new ArrayList<>();
        Reader reader = new InputStreamReader(io);

        try (BufferedReader br = new BufferedReader(reader)) {
            br.readLine();
            for (int i = 0; i < linesAmount; i++) {
                lines.add(br.readLine());
            }
        } catch (IOException e) {
            System.out.println(IO_EXCEPTION_MESSAGE.getMessage());
        }
        return lines;
    }

    /**
     * Построчное чтение всех строк из потока
     *
     * @param io Поток для чтения файла
     * @return Список считанных строк
     */
    public static List<String> readAllFromFile(InputStream io) {
        List<String> lines = new ArrayList<>();
        Reader reader = new InputStreamReader(io);

        try (BufferedReader br = new BufferedReader(reader)) {
            br.readLine();
            while (br.ready()) {
                lines.add(br.readLine());
            }
        } catch (IOException e) {
            System.out.println(IO_EXCEPTION_MESSAGE.getMessage());
        }
        return lines;
    }
}
