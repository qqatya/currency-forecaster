package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов валют
 */
public enum CurrencyType {
    EUR("EUR", "EUR.csv"),
    USD("USD", "USD.csv"),
    TRY("TRY", "TRY.csv"),
    DEF("DEF", "def");

    private final String commandPart;
    private final String filePath;
    public static final Map<String, CurrencyType> map;

    static {
        map = new HashMap<>();
        for (CurrencyType v : CurrencyType.values()) {
            map.put(v.commandPart, v);
        }
    }

    CurrencyType(String commandPart, String filePath) {
        this.commandPart = commandPart;
        this.filePath = filePath;
    }

    public static CurrencyType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public String getFilePath() {
        return filePath;
    }
}