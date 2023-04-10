package ru.liga.currencyforecaster.enums;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов команд
 */
@RequiredArgsConstructor
public enum CommandEnum {
    RATE("rate"),
    Q("q"),
    DEF("def");

    private final String commandPart;
    private static final Map<String, CommandEnum> map;

    static {
        map = new HashMap<>();
        for (CommandEnum v : CommandEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static CommandEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

}
