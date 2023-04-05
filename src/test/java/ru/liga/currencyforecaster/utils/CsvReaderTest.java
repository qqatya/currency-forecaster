package ru.liga.currencyforecaster.utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvReaderTest {
    String s = """
            nominal;data;curs;cdx
            100;29.03.2023;19,7260;Армянский драм
            100;28.03.2023;19,7066;Армянский драм
            100;25.03.2023;19,6995;Армянский драм
            100;24.03.2023;19,6501;Армянский драм
            100;23.03.2023;19,8254;Армянский драм
            100;22.03.2023;19,7856;Армянский драм
            100;21.03.2023;19,8832;Армянский драм
            100;18.03.2023;19,7292;Армянский драм
            100;17.03.2023;19,6612;Армянский драм""";
    InputStream io = new ByteArrayInputStream(s.getBytes());

    @Test
    public void readsAllLinesFromFile() {
        List<String> result = CsvReader.readAllFromFile(io);

        assertEquals(9, result.size());
    }

    @Test
    public void reads7LinesFromFile() {
        List<String> result = CsvReader.readFromFile(io, 7);

        assertEquals(7, result.size());
    }

}