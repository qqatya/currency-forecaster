package ru.liga.currencyforecaster.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление промежутков для расчетов
 */
@RequiredArgsConstructor
public enum ForecastRangeEnum {
    TOMORROW("tomorrow", 1),
    WEEK("week", 7),
    MONTH("month", 30),
    DEF("def", 0);

    private final String commandPart;
    @Getter
    private final int day;
    private static final Map<String, ForecastRangeEnum> map;

    static {
        map = new HashMap<>();
        for (ForecastRangeEnum v : ForecastRangeEnum.values()) {
            map.put(v.commandPart, v);
        }
    }

    public static ForecastRangeEnum findByCommand(String commandPart) {
        return map.getOrDefault(commandPart, DEF);
    }

}
