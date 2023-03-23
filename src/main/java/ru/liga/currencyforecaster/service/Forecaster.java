package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.parser.CommandParser;
import ru.liga.currencyforecaster.service.parser.CsvParser;
import ru.liga.currencyforecaster.service.validator.CommandValidator;
import ru.liga.currencyforecaster.utils.ConsolePrinter;
import ru.liga.currencyforecaster.utils.CsvReader;

import java.util.List;
import java.util.Scanner;

import static ru.liga.currencyforecaster.model.type.CommandIndex.COMMAND_TYPE_INDEX;
import static ru.liga.currencyforecaster.model.type.ConsoleMessage.ENTER_COMMAND;

public class Forecaster {
    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 7;

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.print(ConsolePrinter.printCurrencies());
        System.out.print(ConsolePrinter.printCommand());
        do {
            System.out.println(ENTER_COMMAND.getMessage());
            command = scanner.nextLine();
            String[] parsedCommand = CommandParser.parseCommand(command);
            CommandValidator commandValidator = new CommandValidator(parsedCommand);

            if (commandValidator.getErrorMessage() != null) {
                System.out.println(commandValidator.getErrorMessage());
            } else if (CommandType.findByCommand(parsedCommand[COMMAND_TYPE_INDEX.getIndex()]) != CommandType.Q) {
                System.out.print(ConsolePrinter.printResult(createResultRates(parsedCommand)));
            }
        } while (CommandType.findByCommand(command) != CommandType.Q);
    }

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param parsedCommand Команда пользователя
     * @return Список сущностей с прогнозом курсов валют
     */
    private static List<Currency> createResultRates(String[] parsedCommand) {
        CurrencyType targetCurrencyType = CommandParser.findCurrencyType(parsedCommand);
        int targetDaysAmount = CommandParser.findDaysAmount(parsedCommand);
        List<Currency> currencies = CsvParser.parseFile(CsvReader.
                readFromFile(CsvReader.loadFileByCurrency(targetCurrencyType), RECORDS_AMOUNT));

        return CurrencyForecasterAvg.predictRateForSomeDays(currencies, targetDaysAmount);
    }

}
