package ru.liga.currencyforecaster.service.parser;

import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.KeyType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.liga.currencyforecaster.model.type.CommandIndex.COMMAND_TYPE_INDEX;
import static ru.liga.currencyforecaster.model.type.CommandIndex.CURRENCY_TYPE_INDEX;

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
        Map<KeyType, String> tempKeys = parseKeys(parsedCommand);
        Set<CurrencyType> tempCur = parseCurrencies(parsedCommand[CURRENCY_TYPE_INDEX.getIndex()]);

        if (type == CommandType.Q || tempKeys.isEmpty() || tempCur.isEmpty()) {
            return Command.getDefaultCommand();
        }
        return new Command(type, tempCur, tempKeys);
    }

    private static Map<KeyType, String> parseKeys(String[] parsedCommand) {
        Map<KeyType, String> keys = new HashMap<>();

        for (int i = 2; i < parsedCommand.length; i += 2) {
            KeyType current = KeyType.findByCommand(parsedCommand[i]);

            if (current != KeyType.DEF) {
                keys.put(current, parsedCommand[i + 1]);
            }
        }
        return keys;
    }

    private static Set<CurrencyType> parseCurrencies(String currencies) {
        String[] split = currencies.split(",");
        Set<CurrencyType> parsedCurrencies = new HashSet<>();

        for (String s : split) {
            CurrencyType current = CurrencyType.findByCommand(s);

            if (current != CurrencyType.DEF) {
                parsedCurrencies.add(current);
            }
        }
        return parsedCurrencies;
    }
}
