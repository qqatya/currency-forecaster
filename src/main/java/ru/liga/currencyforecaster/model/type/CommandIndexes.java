package ru.liga.currencyforecaster.model.type;

/**
 * Перечисление констант для работы с командами
 */
public enum CommandIndexes {
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

    private final int index;

    CommandIndexes(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
