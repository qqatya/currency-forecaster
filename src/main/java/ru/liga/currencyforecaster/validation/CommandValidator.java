package ru.liga.currencyforecaster.validation;

import lombok.Getter;
import ru.liga.currencyforecaster.enums.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static ru.liga.currencyforecaster.enums.CommandIndexEnum.COMMAND_TYPE_INDEX;
import static ru.liga.currencyforecaster.enums.CommandIndexEnum.CURRENCY_TYPE_INDEX;
import static ru.liga.currencyforecaster.enums.DelimiterEnum.COMMA;
import static ru.liga.currencyforecaster.enums.DelimiterEnum.WHITESPACES;
import static ru.liga.currencyforecaster.enums.MessageEnum.*;

public class CommandValidator {
    @Getter
    private String errorMessage;

    public CommandValidator(String command) {
        String[] parsedCommand = command.split(WHITESPACES.getValue());
        validate(parsedCommand);
    }

    private boolean validate(String[] command) {
        if (command.length != 0
                && CommandEnum.findByCommand(command[COMMAND_TYPE_INDEX.getIndex()]) == CommandEnum.Q) {
            return true;
        }
        return validateCommandType(command[COMMAND_TYPE_INDEX.getIndex()])
                && validateCommandLength(command)
                && validateCurrencyType(command[CURRENCY_TYPE_INDEX.getIndex()])
                && validateKeys(command, command[CURRENCY_TYPE_INDEX.getIndex()]);
    }

    private boolean validateCommandLength(String[] command) {
        if (command.length % 2 != 0) {
            errorMessage = KEY_VALUE_MISMATCH.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateCommandType(String value) {
        CommandEnum commandType = CommandEnum.findByCommand(value);

        if (commandType == CommandEnum.DEF) {
            errorMessage = ILLEGAL_COMMAND.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateCurrencyType(String value) {
        String[] split = value.split(COMMA.getValue());
        boolean ifExists = false;

        for (String s : split) {
            if (CurrencyTypeEnum.findByCommand(s) != CurrencyTypeEnum.DEF) {
                ifExists = true;
            } else {
                errorMessage = UNAVAILABLE_CURRENCY.getMessage();
                ifExists = false;
            }
        }
        return ifExists;
    }

    private boolean validateKeys(String[] command, String currency) {
        Map<KeyEnum, String> keys = new HashMap<>();
        int keyPlacement = 2;

        for (int i = keyPlacement; i < command.length; i += keyPlacement) {
            KeyEnum current = KeyEnum.findByCommand(command[i]);

            if (current == KeyEnum.DEF) {
                errorMessage = KEY_DOESNT_EXIST.getMessage() + command[i];
                return false;
            }
            keys.put(current, command[i + 1]);
        }
        if (keys.containsKey(KeyEnum.DATE) && keys.containsKey(KeyEnum.PERIOD)) {
            errorMessage = DATE_PERIOD_CONFLICT.getMessage();
            return false;
        }
        if ((keys.containsKey(KeyEnum.DATE) && keys.containsKey(KeyEnum.OUTPUT))
                || (keys.containsKey(KeyEnum.PERIOD) && !keys.containsKey(KeyEnum.OUTPUT))) {
            errorMessage = OUTPUT_CONFLICT.getMessage();
            return false;
        }

        return validateValues(keys) && validateGraph(currency, keys);
    }

    private boolean validateValues(Map<KeyEnum, String> values) {
        boolean isValid = false;

        for (Map.Entry<KeyEnum, String> entry : values.entrySet()) {
            String current = entry.getValue();

            switch (entry.getKey()) {
                case DATE -> isValid = validateDate(current);
                case ALG -> isValid = validateAlgorithm(current);
                case PERIOD -> isValid = validatePeriod(current);
                case OUTPUT -> isValid = validateOutput(current);
            }
        }
        return isValid;
    }

    private boolean validateDate(String value) {
        if (ForecastRangeEnum.findByCommand(value) == ForecastRangeEnum.TOMORROW) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            errorMessage = INVALID_DATE.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateAlgorithm(String value) {
        if (AlgorithmTypeEnum.findByCommand(value) == AlgorithmTypeEnum.DEF) {
            errorMessage = INVALID_ALGORITHM.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validatePeriod(String value) {
        ForecastRangeEnum period = ForecastRangeEnum.findByCommand(value);

        if (period == ForecastRangeEnum.DEF || period == ForecastRangeEnum.TOMORROW) {
            errorMessage = INVALID_PERIOD.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validateOutput(String value) {
        if (OutputTypeEnum.findByCommand(value) == OutputTypeEnum.DEF) {
            errorMessage = INVALID_OUTPUT.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validateGraph(String currency, Map<KeyEnum, String> keys) {
        String[] split = currency.split(COMMA.getValue());
        String graph = OutputTypeEnum.GRAPH.getCommandPart();

        if (split.length > 1 && !keys.containsValue(graph)) {
            errorMessage = MULTIPLE_CURRENCIES_GRAPH.getMessage();
            return false;
        }
        return true;
    }
}
