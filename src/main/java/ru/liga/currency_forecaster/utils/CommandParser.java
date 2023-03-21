package ru.liga.currency_forecaster.utils;

import ru.liga.currency_forecaster.model.CommandType;
import ru.liga.currency_forecaster.model.CurrencyType;
import ru.liga.currency_forecaster.model.ForecastRange;

import static ru.liga.currency_forecaster.model.ConsoleReferenceBook.*;

public class CommandParser {

    /**
     * Максимальное количество слов в команде для расчета прогноза
     */
    private static final int RATE_COMMAND_LENGTH = 3;

    /**
     * Индекс значения начала команды для расчета прогноза
     */
    private static final int COMMAND_TYPE_INDEX = 0;

    /**
     * Индекс значения валюты в команде для расчета прогноза
     */
    private static final int CURRENCY_TYPE_INDEX = 1;

    /**
     * Индекс количества дней в команде для расчета прогноза
     */
    private static final int DAYS_AMOUNT_INDEX = 2;

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
        if (command.length == RATE_COMMAND_LENGTH) {
            CurrencyType currencyType = CurrencyType.findByCommand(command[CURRENCY_TYPE_INDEX]);

            if (currencyType != CurrencyType.DEF) {
                return currencyType;
            }
            System.out.println(UNAVAILABLE_CURRENCY);
        } else {
            System.out.println(ILLEGAL_COMMAND);
        }
        return null;
    }

    /**
     * Сопоставление строчного значения количества дней с его целочисленным представлением
     *
     * @param command Команда пользователя
     * @return Целочисленное значения количества дней, на которое нужно рассчитать прогноз
     */
    public static int findDaysAmount(String[] command) {
        if (command.length == RATE_COMMAND_LENGTH) {
            int daysAmount = ForecastRange.findByCommand(command[DAYS_AMOUNT_INDEX]).getRange();

            if (daysAmount == 0) {
                System.out.println(UNAVAILABLE_DAYS_AMOUNT);
            }
            return daysAmount;
        }
        return 0;
    }

    /**
     * Сопоставление строчного значения типа команды с перечислением в коде
     *
     * @param command
     * @return Значение типа команды в формате перечисления
     */
    public static CommandType findCommandType(String[] command) {
        if (command.length == RATE_COMMAND_LENGTH) {
            CommandType commandType = CommandType.findByCommand(command[COMMAND_TYPE_INDEX]);

            if (commandType != CommandType.DEF) {
                return commandType;
            }
        }
        System.out.println(ILLEGAL_COMMAND);
        return null;
    }
}
