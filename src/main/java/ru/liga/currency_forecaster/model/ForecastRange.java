package ru.liga.currency_forecaster.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление промежутков для расчетов
 */
public enum ForecastRange {
    TOMORROW("tomorrow", 1),
    WEEK("week", 7),
    DEF("def", 0);

    public static final Map<String, ForecastRange> map;

    static {
        map = new HashMap<>();
        for (ForecastRange v : ForecastRange.values()) {
            map.put(v.commandPart, v);
        }
    }

    private final String commandPart;
    private final int range;

    ForecastRange(String commandPart, int range) {
        this.commandPart = commandPart;
        this.range = range;
    }

    public static ForecastRange findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public int getRange() {
        return range;
    }
}
