package ru.liga.currencyforecaster.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.service.Forecaster;
import ru.liga.currencyforecaster.utils.ConsolePrinter;

/**
 * Обработка сообщения, не являющегося командой (т.е. обычного текста не начинающегося с "/")
 */
public class NonCommand {

    public Answer nonCommandExecute(Long chatId, String userName, String text) {
        //String answer;
        System.out.println("nonCommandExecute");
        try {
            System.out.println(text);
            return Forecaster.processForecast(chatId, userName, text);
        } catch (Exception e) {
            SendMessage message = new SendMessage();
            message.setText("Простите, я не понимаю Вас. Возможно, Вам поможет /help");
            return new Answer(chatId, userName, message);
        }
    }
}