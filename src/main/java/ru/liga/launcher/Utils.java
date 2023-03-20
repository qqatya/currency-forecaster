package ru.liga.launcher;

import ru.liga.currency.CurrencyType;

import java.io.InputStream;

public class Utils {

    /**
     * Максимальное количество слов в команде для расчета прогноза
     */
    private static final int RATE_COMMAND_LENGTH = 3;

    /**
     * Индекс значения валюты в команде для расчета прогноза
     */
    private static final int CURRENCY_TYPE_INDEX = 1;

    /**
     * Индекс количества дней в команде для расчета прогноза
     */
    private static final int DAYS_AMOUNT_INDEX = 2;

    public static void printCurrencies() {
        StringBuilder values = new StringBuilder();
        values.append("Перечень доступных валют:\n");

        for (CurrencyType value : CurrencyType.values()) {
            values.append("- ")
                    .append(value)
                    .append("\n");
        }
        System.out.println(values);
    }

    public static void printCommand() {
        System.out.println("Примеры доступных команд:\n- rate TRY tomorrow\n- rate USD week\n- q (выход)\n");
    }

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
            switch (command[CURRENCY_TYPE_INDEX]) {
                case "EUR":
                    return CurrencyType.EUR;
                case "USD":
                    return CurrencyType.USD;
                case "TRY":
                    return CurrencyType.TRY;
                default:
                    System.out.println("Валюта не поддерживается.");
                    break;
            }
        } else {
            System.out.println("Введена неверная команда.");
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
            switch (command[DAYS_AMOUNT_INDEX]) {
                case "tomorrow":
                    return 1;
                case "week":
                    return 7;
                default:
                    System.out.println("Количество дней недоступно для расчета.");
            }
        }
        return 0;
    }

    /**
     * Поиск файла со статистикой по типу валюты
     *
     * @param currencyType Тип валюты
     * @return Потоковое представление файла
     */
    public static InputStream findFileByCurrency(CurrencyType currencyType) {
        switch (currencyType) {
            case EUR:
                return Main.class.getResourceAsStream("EUR.csv");
            case USD:
                return Main.class.getResourceAsStream("USD.csv");
            case TRY:
                return Main.class.getResourceAsStream("TRY.csv");
            default:
                System.out.println("Валюта не поддерживается.");
                break;
        }
        return null;
    }
}
