package ru.liga.currency_forecaster.service;

import ru.liga.currency_forecaster.model.CommandType;
import ru.liga.currency_forecaster.model.Currency;
import ru.liga.currency_forecaster.model.CurrencyType;
import ru.liga.currency_forecaster.utils.CommandParser;
import ru.liga.currency_forecaster.utils.CsvParser;
import ru.liga.currency_forecaster.utils.CsvReader;
import ru.liga.currency_forecaster.utils.FileSearcher;

import java.util.List;
import java.util.Scanner;

import static ru.liga.currency_forecaster.model.ConsoleReferenceBook.*;

public class CurrencyForecasterAppService {
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        printCurrencies();
        printCommand();
        while (true) {
            System.out.println(ENTER_COMMAND);
            String command = scanner.nextLine();

            if (command.equals("q")) {
                break;
            }
            String[] parsedCommand = CommandParser.parseCommand(command);

            CommandType targetCommandType = CommandParser.findCommandType(parsedCommand);
            CurrencyType targetCurrencyType = CommandParser.findCurrencyType(parsedCommand);
            int targetDaysAmount = CommandParser.findDaysAmount(parsedCommand);

            if (targetCommandType == null || targetCurrencyType == null || targetDaysAmount == 0) {
                continue;
            }
            List<Currency> resultRates = createResultRates(targetCurrencyType, targetDaysAmount);

            printResult(resultRates);
        }
    }

    public static void printCurrencies() {
        StringBuilder values = new StringBuilder();
        values.append(NOTIFY_ABOUT_CURRENCIES);

        for (CurrencyType value : CurrencyType.values()) {
            values.append("- ")
                    .append(value)
                    .append("\n");
        }
        System.out.println(values);
    }

    public static void printCommand() {
        System.out.println(NOTIFY_ABOUT_COMMANDS + COMMAND_EXAMPLES);
    }

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param currencyType Тип валюты
     * @param daysAmount   Количество дней для прогноза
     * @return Список сущностей с прогнозом курсов валют
     */
    private static List<Currency> createResultRates(CurrencyType currencyType, int daysAmount) {
        List<Currency> currencies = CsvParser.parseFile(CsvReader.
                readFromFile(FileSearcher.findFileByCurrency(currencyType)));

        return CurrencyForecasterService.predictRateForSomeDays(currencies, daysAmount);
    }

    private static void printResult(List<Currency> resultRates) {
        for (Currency resultRate : resultRates) {
            System.out.println(resultRate);
        }
    }
}
