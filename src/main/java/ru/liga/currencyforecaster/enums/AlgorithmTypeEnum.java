package ru.liga.currencyforecaster.enums;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление типов алгоритмов
 */
@AllArgsConstructor
public enum AlgorithmTypeEnum {
    AVG("avg"),
    LYEAR("lyear"),
    MYST("mist"),
    REGR("regr"),
    DEF("def");

    private final String commandPart;
    private static final Map<String, AlgorithmTypeEnum> map;

    static {
        map = new HashMap<>();
        for (AlgorithmTypeEnum v : AlgorithmTypeEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static AlgorithmTypeEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

}
