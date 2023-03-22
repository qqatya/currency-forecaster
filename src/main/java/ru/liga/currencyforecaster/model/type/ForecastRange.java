package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление промежутков для расчетов
 */
public enum ForecastRange {
    TOMORROW("tomorrow", 1),
    WEEK("week", 7),
    DEF("def", 0);

    private final String command;
    private final int day;
    public static final Map<String, ForecastRange> map;

    static {
        map = new HashMap<>();
        for (ForecastRange v : ForecastRange.values()) {
            map.put(v.command, v);
        }
    }

    ForecastRange(String command, int day) {
        this.command = command;
        this.day = day;
    }

    public static ForecastRange findByCommand(String command) {
        if (!map.containsKey(command)) {
            return DEF;
        }
        return map.get(command);
    }

    public int getDay() {
        return day;
    }
}
