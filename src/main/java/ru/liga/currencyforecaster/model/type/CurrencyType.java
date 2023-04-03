package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов валют
 */
public enum CurrencyType {
    EUR("EUR", "Евро", "EUR.csv"),
    USD("USD", "Доллар США", "USD.csv"),
    TRY("TRY", "Турецкая лира", "TRY.csv"),
    AMD("AMD", "Армянский драм", "AMD.csv"),
    BGN("BGN", "Болгарский лев", "BGN.csv"),
    DEF("DEF", "Def", "def");

    private final String commandPart;
    private final String currencyName;
    private final String filePath;
    public static final Map<String, CurrencyType> map;

    static {
        map = new HashMap<>();
        for (CurrencyType v : CurrencyType.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static final Map<String, CurrencyType> currencyNameMap;

    static {
        currencyNameMap = new HashMap<>();
        for (CurrencyType v : CurrencyType.values()) {
            currencyNameMap.put(v.currencyName, v);
        }
    }


    CurrencyType(String commandPart, String currencyName, String filePath) {
        this.commandPart = commandPart;
        this.currencyName = currencyName;
        this.filePath = filePath;
    }

    public static CurrencyType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public static CurrencyType findByCurrencyName(String currencyName) {
        if (!currencyNameMap.containsKey(currencyName)) {
            return DEF;
        }
        return currencyNameMap.get(currencyName);
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getFilePath() {
        return filePath;
    }
}