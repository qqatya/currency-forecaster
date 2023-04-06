package ru.liga.currencyforecaster.controller;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.enums.CommandEnum;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.enums.KeyEnum;
import ru.liga.currencyforecaster.model.Command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.liga.currencyforecaster.enums.CommandIndexEnum.COMMAND_TYPE_INDEX;
import static ru.liga.currencyforecaster.enums.CommandIndexEnum.CURRENCY_TYPE_INDEX;

@Slf4j
public class CommandParsingController {
    /**
     * Парсинг команды пользователя в объект Command
     *
     * @param command Введенная команда
     * @return Объект типа Command
     */
    public static Command parseCommand(String command) {
        log.debug("Start parsing command");
        String[] parsedCommand = command.split(" ");
        CommandEnum type = CommandEnum.findByCommand(parsedCommand[COMMAND_TYPE_INDEX.getIndex()]);
        Map<KeyEnum, String> tempKeys = parseKeys(parsedCommand);
        Set<CurrencyTypeEnum> tempCur = parseCurrencies(parsedCommand[CURRENCY_TYPE_INDEX.getIndex()]);

        if (type == CommandEnum.Q || tempKeys.isEmpty() || tempCur.isEmpty()) {
            log.debug("No keys or currencies match. Built default command");
            return Command.getDefaultCommand();
        }
        log.debug("Built command");
        return new Command(type, tempCur, tempKeys);
    }

    private static Map<KeyEnum, String> parseKeys(String[] parsedCommand) {
        Map<KeyEnum, String> keys = new HashMap<>();
        int keyPlacement = 2;

        for (int i = keyPlacement; i < parsedCommand.length; i += keyPlacement) {
            KeyEnum current = KeyEnum.findByCommand(parsedCommand[i]);

            if (current != KeyEnum.DEF) {
                keys.put(current, parsedCommand[i + 1]);
            }
        }
        return keys;
    }

    private static Set<CurrencyTypeEnum> parseCurrencies(String currencies) {
        String[] split = currencies.split(",");
        Set<CurrencyTypeEnum> parsedCurrencies = new HashSet<>();

        for (String s : split) {
            CurrencyTypeEnum current = CurrencyTypeEnum.findByCommand(s);

            if (current != CurrencyTypeEnum.DEF) {
                parsedCurrencies.add(current);
            }
        }
        return parsedCurrencies;
    }
}