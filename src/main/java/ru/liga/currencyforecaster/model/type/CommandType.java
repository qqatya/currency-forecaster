package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов команд
 */
public enum CommandType {
    RATE("rate"),
    Q("q"),
    DEF("def");

    private final String commandPart;
    public static final Map<String, CommandType> map;

    static {
        map = new HashMap<>();
        for (CommandType v : CommandType.values()) {
            map.put(v.commandPart, v);
        }
    }

    CommandType(String commandPart) {
        this.commandPart = commandPart;
    }

    public static CommandType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }
}
