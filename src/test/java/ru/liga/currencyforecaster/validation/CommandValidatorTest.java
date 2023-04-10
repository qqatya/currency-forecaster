package ru.liga.currencyforecaster.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.liga.currencyforecaster.enums.MessageEnum.*;

public class CommandValidatorTest {
    CommandValidator validator;

    @Test
    public void errorMessageIsNullWhenValidCommandPassedAsArgument() {
        String command = "rate USD,AMD,BGN,TRY -period month -alg mist -output graph";

        validator = new CommandValidator(command);
        assertNull(validator.getErrorMessage());
    }

    @Test
    public void keyValueMismatchWhenCommandLengthIsNotEven() {
        String command = "rate USD -period";

        validator = new CommandValidator(command);
        assertEquals(KEY_VALUE_MISMATCH.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void illegalCommandWhenCommandTypeIsDef() {
        String command = "update USD -date 29.05.2023 -alg avg";

        validator = new CommandValidator(command);
        assertEquals(ILLEGAL_COMMAND.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void unavailableCurrencyWhenCurrencyTypeIsDef() {
        String command = "rate JPY -date 29.05.2023 -alg avg";

        validator = new CommandValidator(command);
        assertEquals(UNAVAILABLE_CURRENCY.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void keyDoesntExistWhenKeyTypeIsDef() {
        String command = "rate EUR -data 29.05.2023 -alg avg";

        validator = new CommandValidator(command);
        assertEquals(KEY_DOESNT_EXIST.getMessage() + "-data", validator.getErrorMessage());
    }

    @Test
    public void datePeriodConflictWhenCommandContainsDateAndPeriod() {
        String command = "rate EUR -date 29.05.2023 -period week -alg avg";

        validator = new CommandValidator(command);
        assertEquals(DATE_PERIOD_CONFLICT.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void outputConflictWhenOutputWithoutPeriod() {
        String command1 = "rate EUR -date 29.05.2023 -alg avg -output list";

        validator = new CommandValidator(command1);
        assertEquals(OUTPUT_CONFLICT.getMessage(), validator.getErrorMessage());
        String command2 = "rate EUR -period week -alg avg";

        validator = new CommandValidator(command2);
        assertEquals(OUTPUT_CONFLICT.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void invalidDateWhenDateIsOutOfFormat() {
        String command = "rate EUR -date 29.05.23 -alg avg";

        validator = new CommandValidator(command);
        assertEquals(INVALID_DATE.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void invalidAlgorithmWhenAlgorithmTypeIsDef() {
        String command = "rate EUR -date 29.05.2023 -alg lol";

        validator = new CommandValidator(command);
        assertEquals(INVALID_ALGORITHM.getMessage() + "lol", validator.getErrorMessage());
    }

    @Test
    public void invalidPeriodWhenPeriodIsTomorrowOrDef() {
        String command1 = "rate EUR -period tomorrow -alg avg -output list";

        validator = new CommandValidator(command1);
        assertEquals(INVALID_PERIOD.getMessage() + "tomorrow", validator.getErrorMessage());
        String command2 = "rate EUR -period yesterday -alg avg -output list";

        validator = new CommandValidator(command2);
        assertEquals(INVALID_PERIOD.getMessage() + "yesterday", validator.getErrorMessage());
    }

    @Test
    public void invalidPeriodWhenPeriodIsDef() {
        String command = "rate EUR -period n -alg avg -output list";

        validator = new CommandValidator(command);
        assertEquals(INVALID_PERIOD.getMessage() + "n", validator.getErrorMessage());
    }

    @Test
    public void invalidOutputWhenOutputTypeIsDef() {
        String command = "rate EUR -period week -alg avg -output n";

        validator = new CommandValidator(command);
        assertEquals(INVALID_OUTPUT.getMessage() + "n", validator.getErrorMessage());
    }

    @Test
    public void multipleCurrenciesGraphWhenMoreThan1CurrencyAndKeyGraphIsAbsent() {
        String command = "rate EUR,TRY -period week -alg avg -output n";

        validator = new CommandValidator(command);
        assertEquals(MULTIPLE_CURRENCIES_GRAPH.getMessage(), validator.getErrorMessage());
    }

    @Test
    public void returnsIllegalCommandIfLengthIsEvenButNotAllKeysArePresent() {
        String command = "rate EUR -period week";

        validator = new CommandValidator(command);
        assertEquals(ILLEGAL_COMMAND.getMessage(), validator.getErrorMessage());
    }
}