package ru.liga.currency_forecaster.model;

/**
 * Строковые константы
 */
public class ConsoleReferenceBook {
    public static final String NOTIFY_ABOUT_CURRENCIES = "Перечень доступных валют:\n";
    public static final String NOTIFY_ABOUT_COMMANDS = "Примеры доступных команд:\n";
    public static final String ENTER_COMMAND = "Введите команду:";
    public static final String COMMAND_EXAMPLES = "- rate TRY tomorrow\n- rate USD week\n- q (выход)\n";

    public static final String UNAVAILABLE_CURRENCY = "Валюта не поддерживается.";
    public static final String ILLEGAL_COMMAND = "Введена неверная команда.";
    public static final String UNAVAILABLE_DAYS_AMOUNT = "Количество дней недоступно для расчета.";
}
