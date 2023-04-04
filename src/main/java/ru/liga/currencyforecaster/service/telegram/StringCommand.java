package ru.liga.currencyforecaster.service.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.service.Forecaster;

/**
 * Обработка команд в строчном формате
 */
@Slf4j
public class StringCommand {

    public Answer stringCommandExecute(Long chatId, String userName, String text) {
        log.debug("Executing command: {}", text);
        try {
            log.info("Start processing forecast");
            return Forecaster.processForecast(chatId, userName, text);
        } catch (Exception e) {
            log.error("An error occurred while executing command: {}", e.getMessage());
            SendMessage message = new SendMessage();
            message.setText("Простите, я не понимаю Вас. Проверьте корректность команды.");
            log.debug("Sending error message");
            return new Answer(chatId, userName, message);
        }
    }
}