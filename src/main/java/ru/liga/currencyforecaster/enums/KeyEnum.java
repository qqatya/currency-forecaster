package ru.liga.currencyforecaster.enums;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление ключей команд
 */
@RequiredArgsConstructor
public enum KeyEnum {
    DATE("-date"),
    ALG("-alg"),
    PERIOD("-period"),
    OUTPUT("-output"),
    DEF("def");

    private final String commandPart;
    private static final Map<String, KeyEnum> map;

    static {
        map = new HashMap<>();
        for (KeyEnum v : KeyEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static KeyEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

}
