package ru.liga.currencyforecaster.service.validator;

import ru.liga.currencyforecaster.model.type.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static ru.liga.currencyforecaster.model.type.CommandIndex.COMMAND_TYPE_INDEX;
import static ru.liga.currencyforecaster.model.type.CommandIndex.CURRENCY_TYPE_INDEX;
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
        CommandType commandType = CommandType.findByCommand(value);

        if (commandType == CommandType.DEF) {
            errorMessage = ILLEGAL_COMMAND.getMessage();
            return false;
        }
        return true;
    }

    private boolean validateCurrencyType(String value) {
        String[] split = value.split(",");
        boolean ifExists = false;

        for (String s : split) {
            if (CurrencyType.findByCommand(s) != CurrencyType.DEF) {
                ifExists = true;
            } else {
                errorMessage = UNAVAILABLE_CURRENCY.getMessage();
                ifExists = false;
            }
        }
        return ifExists;
    }

    private boolean validateKeys(String[] command, String currency) {
        Map<KeyType, String> keys = new HashMap<>();

        for (int i = 2; i < command.length; i += 2) {
            KeyType current = KeyType.findByCommand(command[i]);

            if (current == KeyType.DEF) {
                errorMessage = KEY_DOESNT_EXIST.getMessage() + command[i];
                return false;
            }
            keys.put(current, command[i + 1]);
        }
        if (keys.containsKey(KeyType.DATE) && keys.containsKey(KeyType.PERIOD)) {
            errorMessage = DATE_PERIOD_CONFLICT.getMessage();
            return false;
        }
        if ((keys.containsKey(KeyType.DATE) && keys.containsKey(KeyType.OUTPUT))
                || (keys.containsKey(KeyType.PERIOD) && !keys.containsKey(KeyType.OUTPUT))) {
            errorMessage = OUTPUT_CONFLICT.getMessage();
            return false;
        }

        return validateValues(keys) && validateGraph(currency, keys);
    }

    private boolean validateValues(Map<KeyType, String> values) {
        boolean isValid = false;

        for (Map.Entry<KeyType, String> entry : values.entrySet()) {
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
        if (ForecastRange.findByCommand(value) == ForecastRange.TOMORROW) {
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
        if (AlgorithmType.findByCommand(value) == AlgorithmType.DEF) {
            errorMessage = INVALID_ALGORITHM.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validatePeriod(String value) {
        ForecastRange period = ForecastRange.findByCommand(value);

        if (period == ForecastRange.DEF || period == ForecastRange.TOMORROW) {
            errorMessage = INVALID_PERIOD.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validateOutput(String value) {
        if (OutputType.findByCommand(value) == OutputType.DEF) {
            errorMessage = INVALID_OUTPUT.getMessage() + value;
            return false;
        }
        return true;
    }

    private boolean validateGraph(String currency, Map<KeyType, String> keys) {
        String[] split = currency.split(",");
        String graph = OutputType.GRAPH.getCommandPart();

        if (split.length > 1 && !keys.containsValue(graph)) {
            errorMessage = MULTIPLE_CURRENCIES_GRAPH.getMessage();
            return false;
        }
        return true;
    }
}
