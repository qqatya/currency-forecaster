package ru.liga.currencyforecaster.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvReaderTest {
    private static long lineCount;

    @BeforeAll
    public static void setUp() throws URISyntaxException {
        Path path = Path.of(ClassLoader.getSystemResource("csv/EURTest.csv").toURI());
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            lineCount = stream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readsAllLinesFromFile() {
        List<String> result = CsvReader.readAllFromFile("csv/EURTest.csv");

        assertEquals(lineCount, result.size());
    }

}
