package ru.liga.currencyforecaster.service.validator;

import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;

import static ru.liga.currencyforecaster.model.type.CommandIndex.*;
import static ru.liga.currencyforecaster.model.type.ConsoleMessage.*;

public class CommandValidator {
    private String errorMessage;

    public CommandValidator(String command) {
        String[] parsedCommand = command.split(" ");
        validate(parsedCommand);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean validate(String[] command) {
        if (command.length != 0
                && CommandType.findByCommand(command[COMMAND_TYPE_INDEX.getIndex()]) == CommandType.Q) {
            return true;
        }
        return validateCommandLength(command)
                && validateCommandType(command[COMMAND_TYPE_INDEX.getIndex()])
                && validateCurrencyType(command[CURRENCY_TYPE_INDEX.getIndex()])
                && validateDaysAmount(command[DAYS_AMOUNT_INDEX.getIndex()]);
    }

    private boolean validateCommandLength(String[] command) {
        if (command.length != RATE_COMMAND_LENGTH.getIndex()) {
            errorMessage = ILLEGAL_COMMAND.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateCommandType(String value) {
        CommandType commandType = CommandType.findByCommand(value);

        if (commandType == CommandType.DEF) {
            errorMessage = ILLEGAL_COMMAND.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateCurrencyType(String value) {
        CurrencyType currencyType = CurrencyType.findByCommand(value);

        if (currencyType == CurrencyType.DEF) {
            errorMessage = UNAVAILABLE_CURRENCY.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateDaysAmount(String value) {
        int daysAmount = ForecastRange.findByCommand(value).getDay();

        if (daysAmount == 0) {
            errorMessage = UNAVAILABLE_DAYS_AMOUNT.getMessage();
            return false;
        }
        return true;
    }
}
