package ru.liga.currencyforecaster.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление вариантов вывода результатов расчета
 */
@AllArgsConstructor
public enum OutputTypeEnum {
    LIST("list"),
    GRAPH("graph"),
    DEF("def");

    @Getter
    private final String commandPart;
    private static final Map<String, OutputTypeEnum> map;

    static {
        map = new HashMap<>();
        for (OutputTypeEnum v : OutputTypeEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static OutputTypeEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

}
