package ru.liga.currencyforecaster.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvReader {
    /**
     * Поиск файла со статистикой по типу валюты
     *
     * @param fileName Название файла
     * @return Потоковое представление файла
     */
    public static Path getFilePath(String fileName) {
        try {
            return Path.of(ClassLoader.getSystemResource(fileName).toURI());
        } catch (URISyntaxException e) {
            log.error("File not found: {}", fileName);
        }
        return null;
    }

    /**
     * Построчное чтение всех строк из потока
     *
     * @param path Путь до файла
     * @return Список считанных строк
     */
    public static List<String> readAllFromFile(Path path) {
        List<String> lines = new ArrayList<>();

        log.debug("Start reading all lines from file {}", path.getFileName());
        try {
            lines = Files.readAllLines(path);
            log.debug("Finished reading lines");
        } catch (IOException e) {
            log.error("An error occurred while reading file: {}", e.getMessage());
        }
        return lines;
    }
}
