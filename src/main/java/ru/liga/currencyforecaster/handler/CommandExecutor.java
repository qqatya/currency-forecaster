package ru.liga.currencyforecaster.handler;

import ru.liga.currencyforecaster.model.Answer;

public interface CommandExecutor {
    /**
     * Выполнение команды
     * @param chatId Идентификатор чата, из которого поступила команда
     * @param userName Имя пользователя
     * @param text Текст команды
     * @return Объект Answer, содержащий результаты выполнения команды
     */
    Answer execute(Long chatId, String userName, String text);
}
