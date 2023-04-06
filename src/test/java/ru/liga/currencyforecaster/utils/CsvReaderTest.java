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
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvReaderTest {
    private static long lineCount;
    private static Path path;

    @BeforeAll
    public static void setUp() throws URISyntaxException {
        path = Path.of(ClassLoader.getSystemResource("csv/EURTest.csv").toURI());
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            lineCount = stream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getsPath() {
        Path path = CsvReader.getFilePath("csv/EURTest.csv");

        assertNotNull(path.getFileName());
        assertEquals("EURTest.csv", path.getFileName().toString());
    }

    @Test
    public void readsAllLinesFromFile() {
        List<String> result = CsvReader.readAllFromFile(path);

        assertEquals(lineCount, result.size());
    }

}
