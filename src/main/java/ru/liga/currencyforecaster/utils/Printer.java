package ru.liga.currencyforecaster.utils;

import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.model.Currency;

import java.text.SimpleDateFormat;
import java.util.List;

import static ru.liga.currencyforecaster.enums.MessageEnum.*;

public class Printer {
    public static String printResult(List<Currency> resultRates) {
        StringBuilder values = new StringBuilder();
        OutputFormatter formatter = new OutputFormatter(new SimpleDateFormat("E dd.MM.yyyy"));

        for (Currency resultRate : resultRates) {
            values.append(formatter.printDayAndRate(resultRate))
                    .append("\n");
        }
        return values.toString();
    }

    public static String printCurrencies() {
        StringBuilder values = new StringBuilder();
        values.append(NOTIFY_ABOUT_CURRENCIES.getMessage());

        for (CurrencyTypeEnum value : CurrencyTypeEnum.values()) {
            if (value != CurrencyTypeEnum.DEF) {
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
}
