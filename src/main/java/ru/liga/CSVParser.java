package ru.liga;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static List<CurrencyDto> loadFromFile(InputStream io) {
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        Reader reader = new InputStreamReader(io);
        try {
            BufferedReader br = new BufferedReader(reader);

            br.readLine();
            for (int i = 0; i < 7; i++) {
                String str = br.readLine();

                currencyDtos.add(fromString(str));
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка при считывании файла");
        }
        return currencyDtos;
    }

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
