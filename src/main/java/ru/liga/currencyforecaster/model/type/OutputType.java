package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

public enum OutputType {
    LIST("list"),
    GRAPH("graph"),
    DEF("def");

    private final String commandPart;
    public static final Map<String, OutputType> map;

    static {
        map = new HashMap<>();
        for (OutputType v : OutputType.values()) {
            map.put(v.commandPart, v);
        }
    }

    OutputType(String commandPart) {
        this.commandPart = commandPart;
    }

    public static OutputType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public String getCommandPart() {
        return commandPart;
    }
}
