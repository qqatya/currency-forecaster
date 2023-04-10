package ru.liga.currencyforecaster.enums;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum CsvColumnsEnum {
    NOMINAL("nominal"),
    DATA("data"),
    CURS("curs"),
    CDX("cdx"),
    DEF("def");

    private final String columnName;
    private static final Map<String, CsvColumnsEnum> map;

    static {
        map = new HashMap<>();
        for (CsvColumnsEnum v : CsvColumnsEnum.values()) {
            map.put(v.columnName, v);
        }
    }

    public static CsvColumnsEnum findByCommand(String columnName) {
        return map.getOrDefault(columnName, DEF);
    }

}
