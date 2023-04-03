package ru.liga.currencyforecaster.model.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление промежутков для расчетов
 */
public enum ForecastRange {
    TOMORROW("tomorrow", 1),
    WEEK("week", 7),
    MONTH("month", 30),
    DEF("def", 0);

    private final String commandPart;
    private final int day;
    public static final Map<String, ForecastRange> map;

    static {
        map = new HashMap<>();
        for (ForecastRange v : ForecastRange.values()) {
            map.put(v.commandPart, v);
        }
    }

    ForecastRange(String commandPart, int day) {
        this.commandPart = commandPart;
        this.day = day;
    }

    public static ForecastRange findByCommand(String commandPart) {
        if (!map.containsKey(commandPart)) {
            return DEF;
        }
        return map.get(commandPart);
    }

    public int getDay() {
        return day;
    }
}
