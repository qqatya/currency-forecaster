package ru.liga.currencyforecaster.model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * Класс ответов телеграм-бота
 */
public class Answer {
    private final Long chatId;
    private final String userName;
    private final Boolean isGraph;
    private SendMessage message;
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
        isGraph = false;
    }

    public SendMessage getMessage() {
        return message;
    }

    public SendPhoto getPhoto() {
        return photo;
    }

    public Boolean getGraph() {
        return isGraph;
    }
}
