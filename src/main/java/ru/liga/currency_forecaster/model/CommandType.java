package ru.liga.currency_forecaster.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов команд
 */
public enum CommandType {
    RATE("rate"),
    DEF("def");

    public static final Map<String, CommandType> map;

    static {
        map = new HashMap<>();
        for (CommandType v : CommandType.values()) {
            map.put(v.commandPart, v);
        }
    }

    private final String commandPart;

    CommandType(String commandPart) {
        this.commandPart = commandPart;
    }

    public static CommandType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public String getCommandPart() {
        return commandPart;
    }
}
