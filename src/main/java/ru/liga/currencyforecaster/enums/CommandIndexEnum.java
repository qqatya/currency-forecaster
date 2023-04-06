package ru.liga.currencyforecaster.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление констант для работы с командами
 */
@AllArgsConstructor
public enum CommandIndexEnum {
    /**
     * Индекс значения начала команды для расчета прогноза
     */
    COMMAND_TYPE_INDEX(0),

    /**
     * Индекс значения валюты в команде для расчета прогноза
     */
    CURRENCY_TYPE_INDEX(1),

    /**
     * Индекс количества дней в команде для расчета прогноза
     */
    DAYS_AMOUNT_INDEX(2),

    /**
     * Максимальное количество слов в команде для расчета прогноза
     */
    RATE_COMMAND_LENGTH(3);

    @Getter
    private final int index;

}
