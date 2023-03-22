package ru.liga.currencyforecaster.model.type;

/**
 * Строковые константы
 */
public enum ConsoleMessage {
    NOTIFY_ABOUT_CURRENCIES("Перечень доступных валют:\n"),
    NOTIFY_ABOUT_COMMANDS("Примеры доступных команд:\n"),
    ENTER_COMMAND("Введите команду:"),
    COMMAND_EXAMPLES("- rate TRY tomorrow\n- rate USD week\n- q (выход)\n"),

    UNAVAILABLE_CURRENCY("Валюта не поддерживается."),
    ILLEGAL_COMMAND("Введена неверная команда."),
    UNAVAILABLE_DAYS_AMOUNT("Количество дней недоступно для расчета."),

    IO_EXCEPTION_MESSAGE("Возникла ошибка при считывании файла");

    private final String message;

    ConsoleMessage(String messageText) {
        this.message = messageText;
    }

    public String getMessage() {
        return message;
    }
}
