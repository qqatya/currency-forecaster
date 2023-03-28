package ru.liga.currencyforecaster.service.parser;

import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;

import static ru.liga.currencyforecaster.model.type.CommandIndex.*;

public class CommandParser {
    /**
     * Парсинг команды пользователя в объект Command
     *
     * @param command Введенная команда
     * @return Объект типа Command
     */
    public static Command parseCommand(String command) {
        String[] parsedCommand = command.split(" ");
        CommandType type = CommandType.findByCommand(parsedCommand[COMMAND_TYPE_INDEX.getIndex()]);

        if (type == CommandType.Q) {
            return new Command(type, CurrencyType.DEF, ForecastRange.DEF);
        }
        CurrencyType currency = CurrencyType.findByCommand(parsedCommand[CURRENCY_TYPE_INDEX.getIndex()]);
        ForecastRange range = ForecastRange.findByCommand(parsedCommand[DAYS_AMOUNT_INDEX.getIndex()]);

        return new Command(type, currency, range);
    }
}
