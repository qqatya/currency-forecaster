package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

public enum AlgorithmType {
    AVG("avg"),
    LYEAR("lyear"),
    MYST("mist"),
    REGR("regr"),
    DEF("def");

    private final String commandPart;
    public static final Map<String, AlgorithmType> map;

    static {
        map = new HashMap<>();
        for (AlgorithmType v : AlgorithmType.values()) {
            map.put(v.commandPart, v);
        }
    }

    AlgorithmType(String commandPart) {
        this.commandPart = commandPart;
    }

    public static AlgorithmType findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }


}
