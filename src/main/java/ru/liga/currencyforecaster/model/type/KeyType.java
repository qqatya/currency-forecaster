package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

public enum KeyType {
    DATE("-date"),
    ALG("-alg"),
    PERIOD("-period"),
    OUTPUT("-output"),
    DEF("def");

    private final String commandPart;
    public static final Map<String, KeyType> map;

    static {
        map = new HashMap<>();
        for (KeyType v : KeyType.values()) {
            map.put(v.commandPart, v);
        }
    }

    KeyType(String commandPart) {
        this.commandPart = commandPart;
    }

    public static KeyType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }
}
