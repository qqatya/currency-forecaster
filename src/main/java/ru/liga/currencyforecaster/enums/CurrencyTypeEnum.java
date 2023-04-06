package ru.liga.currencyforecaster.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов валют
 */
@AllArgsConstructor
public enum CurrencyTypeEnum {
    EUR("EUR", "Евро", "csv/EUR.csv"),
    USD("USD", "Доллар США", "csv/USD.csv"),
    TRY("TRY", "Турецкая лира", "csv/TRY.csv"),
    AMD("AMD", "Армянский драм", "csv/AMD.csv"),
    BGN("BGN", "Болгарский лев", "csv/BGN.csv"),
    DEF("DEF", "Def", "def");

    private final String commandPart;
    @Getter
    private final String currencyName;
    @Getter
    private final String path;
    private static final Map<String, CurrencyTypeEnum> map;

    static {
        map = new HashMap<>();
        for (CurrencyTypeEnum v : CurrencyTypeEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    private static final Map<String, CurrencyTypeEnum> currencyNameMap;

    static {
        currencyNameMap = new HashMap<>();
        for (CurrencyTypeEnum v : CurrencyTypeEnum.values()) {
            currencyNameMap.put(v.currencyName, v);
        }
    }

    public static CurrencyTypeEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

    public static CurrencyTypeEnum findByCurrencyName(String currencyName) {
        return currencyNameMap.getOrDefault(currencyName, DEF);
    }

}