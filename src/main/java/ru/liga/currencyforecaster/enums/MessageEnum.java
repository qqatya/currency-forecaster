package ru.liga.currencyforecaster.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Строковые константы
 */
@RequiredArgsConstructor
public enum MessageEnum {
    NOTIFY_ABOUT_CURRENCIES("Перечень доступных валют:\n"),
    NOTIFY_ABOUT_COMMANDS("Примеры доступных команд:\n"),
    ENTER_COMMAND("Введите команду:"),
    COMMAND_EXAMPLES("- rate TRY tomorrow\n- rate USD week\n- q (выход)\n"),

    UNAVAILABLE_CURRENCY("Валюта не поддерживается."),
    ILLEGAL_COMMAND("Введена неверная команда."),
    UNAVAILABLE_DAYS_AMOUNT("Количество дней недоступно для расчета."),

    IO_EXCEPTION_MESSAGE("Возникла ошибка при считывании файла."),
    KEY_DOESNT_EXIST("Введенный ключ отсутствует: "),
    DATE_PERIOD_CONFLICT("Невозможно расчитать прогноз на дату и период одновременно."),
    OUTPUT_CONFLICT("Ключ -output необходимо вводить совместно с -period."),
    INVALID_DATE("Значение ключа -date должно содержать дату в формате ДД.ММ.ГГГГ или tomorrow."),
    INVALID_ALGORITHM("Введен некорректный алгоритм для расчета: "),
    INVALID_PERIOD("Введен некорректный период для расчета: "),
    INVALID_OUTPUT("Введен некорректный вариант вывода: "),
    KEY_VALUE_MISMATCH("Не у всех ключей указаны значения"),
    MULTIPLE_CURRENCIES_GRAPH("Для расчета курса по нескольким валютам используйте ключ -output graph"),
    EMPTY_LIST("Список сущностей, по которым ведется расчет, пустой"),
    ILLEGAL_FILE_STRUCTURE("Некорректная структура файла"),
    EMPTY_SET("Значения валют отсутствуют");

    @Getter
    private final String message;

}
