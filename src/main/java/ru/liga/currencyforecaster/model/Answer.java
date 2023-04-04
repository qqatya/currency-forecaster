package ru.liga.currencyforecaster.model;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * Класс ответов телеграм-бота
 */
public class Answer {
    private final Long chatId;
    private final String userName;
    @Getter
    private final Boolean isGraph;
    @Getter
    private SendMessage message;
    @Getter
    private SendPhoto photo;

    public Answer(Long chatId, String userName, SendPhoto photo) {
        this.chatId = chatId;
        this.userName = userName;
        this.photo = photo;
        this.isGraph = true;
    }

    public Answer(Long chatId, String userName, SendMessage message) {
        this.chatId = chatId;
        this.userName = userName;
        this.message = message;
        this.isGraph = false;
    }
}
