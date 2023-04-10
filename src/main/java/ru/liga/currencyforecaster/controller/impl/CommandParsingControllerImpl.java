package ru.liga.currencyforecaster.controller.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.controller.CommandParsingController;
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
import static ru.liga.currencyforecaster.enums.DelimiterEnum.COMMA;
import static ru.liga.currencyforecaster.enums.DelimiterEnum.WHITESPACES;

@Slf4j
public class CommandParsingControllerImpl implements CommandParsingController {
    @Override
    public Command parseCommand(String command) {
        log.debug("Start parsing command");
        String[] parsedCommand = command.split(WHITESPACES.getValue());
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

    private Map<KeyEnum, String> parseKeys(String[] parsedCommand) {
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

    private Set<CurrencyTypeEnum> parseCurrencies(String currencies) {
        String[] split = currencies.split(COMMA.getValue());
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
