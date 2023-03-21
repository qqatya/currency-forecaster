package ru.liga.currency_forecaster.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    /**
     * Построчное чтение данных из потока
     *
     * @param io Поток для чтения файла
     * @return Список считанных строк
     */
    public static List<String> readFromFile(InputStream io) {
        List<String> lines = new ArrayList<>();
        Reader reader = new InputStreamReader(io);

        try (BufferedReader br = new BufferedReader(reader)) {
            br.readLine();
            while (br.ready()) {
                lines.add(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка при считывании файла");
        }
        return lines;
    }
}
