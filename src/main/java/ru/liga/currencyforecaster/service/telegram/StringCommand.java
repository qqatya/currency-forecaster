package ru.liga.currencyforecaster.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.service.Forecaster;

/**
 * Обработка команд в строчном формате
 */
public class StringCommand {

    public Answer stringCommandExecute(Long chatId, String userName, String text) {
        System.out.println("nonCommandExecute");
        try {
            System.out.println(text);
            return Forecaster.processForecast(chatId, userName, text);
        } catch (Exception e) {
            SendMessage message = new SendMessage();
            message.setText("Простите, я не понимаю Вас. Проверьте корректность команды.");
            return new Answer(chatId, userName, message);
        }
    }
}