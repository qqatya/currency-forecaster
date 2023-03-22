package ru.liga.currencyforecaster.service.validator;

import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;

import static ru.liga.currencyforecaster.model.type.CommandIndexes.*;
import static ru.liga.currencyforecaster.model.type.ConsoleMessage.*;

public class CommandValidator {
    public static boolean validate(String[] command) {
        return validateCommandLength(command)
                && validateCommandType(command[COMMAND_TYPE_INDEX.getIndex()])
                && validateCurrencyType(command[CURRENCY_TYPE_INDEX.getIndex()])
                && validateDaysAmount(command[DAYS_AMOUNT_INDEX.getIndex()]);
    }

    private static boolean validateCommandLength(String[] command) {
        if (command.length != RATE_COMMAND_LENGTH.getIndex()) {
            System.out.println(ILLEGAL_COMMAND.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validateCommandType(String value) {
        CommandType commandType = CommandType.findByCommand(value);

        if (commandType == CommandType.DEF) {
            System.out.println(ILLEGAL_COMMAND.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validateCurrencyType(String value) {
        CurrencyType currencyType = CurrencyType.findByCommand(value);

        if (currencyType == CurrencyType.DEF) {
            System.out.println(UNAVAILABLE_CURRENCY.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validateDaysAmount(String value) {
        int daysAmount = ForecastRange.findByCommand(value).getDay();

        if (daysAmount == 0) {
            System.out.println(UNAVAILABLE_DAYS_AMOUNT.getMessage());
            return false;
        }
        return true;
    }

}
