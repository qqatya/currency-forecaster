package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;
import ru.liga.currencyforecaster.service.parser.CommandParser;
import ru.liga.currencyforecaster.service.parser.CsvParser;
import ru.liga.currencyforecaster.service.validator.CommandValidator;
import ru.liga.currencyforecaster.utils.ConsolePrinter;
import ru.liga.currencyforecaster.utils.CsvReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static ru.liga.currencyforecaster.model.type.ConsoleMessage.ENTER_COMMAND;

public class Forecaster {
    /**
     * Количество записей в файле, по которым расчитывается прогноз
     */
    private static final int RECORDS_AMOUNT = 365;

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        String command;
        Command parsedCommand;

        System.out.print(ConsolePrinter.printCurrencies());
        System.out.print(ConsolePrinter.printCommand());
        do {
            System.out.println(ENTER_COMMAND.getMessage());
            command = scanner.nextLine();
            CommandValidator commandValidator = new CommandValidator(command);

            if (commandValidator.getErrorMessage() != null) {
                System.out.println(commandValidator.getErrorMessage());
                parsedCommand = new Command(CommandType.DEF, CurrencyType.DEF, ForecastRange.DEF);
            } else {
                parsedCommand = CommandParser.parseCommand(command);
            }
            if (parsedCommand.getCurrency() != CurrencyType.DEF || parsedCommand.getRange() != ForecastRange.DEF) {
                System.out.print(ConsolePrinter.printResult(createResultRates(parsedCommand)));
            }
        } while (CommandType.findByCommand(command) != CommandType.Q);
    }

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param command Команда пользователя
     * @return Список сущностей с прогнозом курсов валют
     */
    private static List<Currency> createResultRates(Command command) {
        int targetDaysAmount = command.getRange().getDay();
        List<Currency> currencies = CsvParser.parseFile(CsvReader.
                readAllFromFile(CsvReader.loadFileByCurrency(command.getCurrency())));

        return CurrencyForecasterMystical.predictRateForSomeDays(currencies, LocalDate.now().plusDays(1),
                targetDaysAmount);
    }
}
