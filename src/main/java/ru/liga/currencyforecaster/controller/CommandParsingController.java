package ru.liga.currencyforecaster.controller;

import ru.liga.currencyforecaster.model.Command;

public interface CommandParsingController {
    /**
     * Парсинг команды пользователя в объект Command
     *
     * @param command Введенная команда
     * @return Объект типа Command
     */
    Command parseCommand(String command);
}
