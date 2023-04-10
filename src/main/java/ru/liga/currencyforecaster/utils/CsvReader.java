package ru.liga.currencyforecaster.utils;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.exception.CurrencyFileNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvReader {
    /**
     * Построчное чтение всех строк из потока
     *
     * @param fileName Название файла
     * @return Список считанных строк
     */
    public static List<String> readAllFromFile(String fileName) {
        Path path = getFilePath(fileName);
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

    private static Path getFilePath(String fileName) {
        try {
            return Path.of(ClassLoader.getSystemResource(fileName).toURI());
        } catch (URISyntaxException e) {
            log.error("File not found: {}", fileName);
        }
        throw new CurrencyFileNotFoundException();
    }
}
