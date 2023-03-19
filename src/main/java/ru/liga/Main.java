package ru.liga;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printCurrencies();
        printCommand();

        while (true) {
            System.out.println("Введите команду:");
            String command = scanner.nextLine();

            if (command.equals("q")) break;
            CurrencyType targetCurrency = findCurrencyType(parseCommand(command));
            int targetDaysAmount = findDaysAmount(parseCommand(command));

            if (targetCurrency == null || targetDaysAmount == 0) continue;
            List<CurrencyDto> currencyDtos = CSVParser.loadFromFile(findFileByCurrency(targetCurrency));
            List<CurrencyDto> resultRates = CurrencyForecaster.predictRateForSomeDays(currencyDtos, targetDaysAmount);

            for (CurrencyDto resultRate : resultRates) {
                System.out.println(resultRate);
            }
        }
    }

    public static void printCurrencies() {
        StringBuilder values = new StringBuilder();
        values.append("Перечень доступных валют:\n");

        for (CurrencyType value : CurrencyType.values()) {
            values.append("- ")
                    .append(value)
                    .append("\n");
        }
        System.out.println(values);
    }

    public static void printCommand() {
        System.out.println("Примеры доступных команд:\n- rate TRY tomorrow\n- rate USD week\n- q (выход)\n");
    }

    public static String[] parseCommand(String command) {
        return command.split(" ");
    }

    public static CurrencyType findCurrencyType(String[] command) {
        if (command.length == 3) {
            switch (command[1]) {
                case "EUR":
                    return CurrencyType.EUR;
                case "USD":
                    return CurrencyType.USD;
                case "TRY":
                    return CurrencyType.TRY;
                default:
                    System.out.println("Валюта не поддерживается.");
                    break;
            }
        } else {
            System.out.println("Введена неверная команда.");
        }
        return null;
    }

    public static int findDaysAmount(String[] command) {
        if (command.length == 3) {
            switch (command[2]) {
                case "tomorrow":
                    return 1;
                case "week":
                    return 7;
                default:
                    System.out.println("Количество дней недоступно для расчета.");
            }
        }
        return 0;
    }

    public static InputStream findFileByCurrency(CurrencyType currencyType) {

        switch (currencyType) {
            case EUR:
                return Main.class.getResourceAsStream("EUR.csv");
            case USD:
                return Main.class.getResourceAsStream("USD.csv");
            case TRY:
                return Main.class.getResourceAsStream("TRY.csv");
            default:
                System.out.println("Валюта не поддерживается.");
                break;
        }
        return null;
    }
}