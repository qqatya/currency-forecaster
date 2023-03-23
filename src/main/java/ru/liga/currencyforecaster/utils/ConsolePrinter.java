package ru.liga.currencyforecaster.utils;

import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;

import java.util.List;

import static ru.liga.currencyforecaster.model.type.ConsoleMessage.*;

public class ConsolePrinter {
    public static String printCurrencies() {
        StringBuilder values = new StringBuilder();
        values.append(NOTIFY_ABOUT_CURRENCIES.getMessage());

        for (CurrencyType value : CurrencyType.values()) {
            if (value != CurrencyType.DEF) {
                values.append("- ")
                        .append(value)
                        .append("\n");
            }
        }
        return values.toString();
    }

    public static String printCommand() {
        return NOTIFY_ABOUT_COMMANDS.getMessage() + COMMAND_EXAMPLES.getMessage();
    }

    public static String printResult(List<Currency> resultRates) {
        StringBuilder values = new StringBuilder();
        for (Currency resultRate : resultRates) {
            values.append(resultRate).append("\n");
        }
        return values.toString();
    }
}
