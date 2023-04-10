package ru.liga.currencyforecaster.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DelimiterEnum {
    WHITESPACES("\\s+"),
    SEMICOLON(";"),
    COMMA(","),
    DOT("."),
    EMPTY_STR("");

    @Getter
    private final String value;

}
