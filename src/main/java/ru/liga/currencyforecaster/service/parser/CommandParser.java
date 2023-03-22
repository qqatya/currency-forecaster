package ru.liga.currencyforecaster.service.parser;

import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;

import static ru.liga.currencyforecaster.model.type.CommandIndexes.CURRENCY_TYPE_INDEX;
import static ru.liga.currencyforecaster.model.type.CommandIndexes.DAYS_AMOUNT_INDEX;

public class CommandParser {
    /**
     * Парсинг команды пользователя в массив строк
     *
     * @param command Введенная команда
     * @return Результат парсинга по пробелам
     */
    public static String[] parseCommand(String command) {
        return command.split(" ");
    }

    /**
     * Сопоставление строчного значения валюты с перечислением в коде
     *
     * @param command Команда пользователя
     * @return Значение типа CurrencyType
     */
    public static CurrencyType findCurrencyType(String[] command) {
        return CurrencyType.findByCommand(command[CURRENCY_TYPE_INDEX.getIndex()]);
    }

    /**
     * Сопоставление строчного значения количества дней с его целочисленным представлением
     *
     * @param command Команда пользователя
     * @return Целочисленное значения количества дней, на которое нужно рассчитать прогноз
     */
    public static int findDaysAmount(String[] command) {
        return ForecastRange.findByCommand(command[DAYS_AMOUNT_INDEX.getIndex()]).getDay();
    }
}
