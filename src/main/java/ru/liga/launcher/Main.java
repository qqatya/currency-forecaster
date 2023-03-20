package ru.liga.launcher;

import ru.liga.csv.CSVParser;
import ru.liga.currency.CurrencyDto;
import ru.liga.currency.CurrencyForecaster;
import ru.liga.currency.CurrencyType;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Utils.printCurrencies();
        Utils.printCommand();

        while (true) {
            System.out.println("Введите команду:");
            String command = scanner.nextLine();

            if (command.equals("q")) {
                break;
            }
            CurrencyType targetCurrency = Utils.findCurrencyType(Utils.parseCommand(command));
            int targetDaysAmount = Utils.findDaysAmount(Utils.parseCommand(command));

            if (targetCurrency == null || targetDaysAmount == 0) {
                continue;
            }
            List<CurrencyDto> currencyDtos = CSVParser.loadFromFile(Utils.findFileByCurrency(targetCurrency));
            List<CurrencyDto> resultRates = CurrencyForecaster.predictRateForSomeDays(currencyDtos, targetDaysAmount);

            for (CurrencyDto resultRate : resultRates) {
                System.out.println(resultRate);
            }
        }
    }

}