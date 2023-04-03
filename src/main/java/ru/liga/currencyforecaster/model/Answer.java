package ru.liga.currencyforecaster.model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class Answer {
    private final Long chatId;
    private final String userName;
    private SendMessage message;
    private SendPhoto photo;
    private final Boolean isGraph;

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
