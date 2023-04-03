package ru.liga.currencyforecaster.model;

import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.KeyType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс для хранения команды
 */
public class Command {
    private final CommandType type;
    private final Set<CurrencyType> currency;
    private final Map<KeyType, String> keys;

    public Command(CommandType type, Set<CurrencyType> currency, Map<KeyType, String> keys) {
        this.type = type;
        this.currency = currency;
        this.keys = keys;
    }

    public static Command getDefaultCommand() {
        Set<CurrencyType> tempCur = new HashSet<>();
        Map<KeyType, String> tempKeys = new HashMap();

        tempCur.add(CurrencyType.DEF);
        tempKeys.put(KeyType.DEF, "");
        return new Command(CommandType.DEF, tempCur, tempKeys);
    }

    public CommandType getType() {
        return type;
    }

    public Set<CurrencyType> getCurrency() {
        return currency;
    }

    public Map<KeyType, String> getKeys() {
        return keys;
    }
}
