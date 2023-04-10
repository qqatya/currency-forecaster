package ru.liga.currencyforecaster.service.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.currencyforecaster.factory.CommandExecutorFactory;
import ru.liga.currencyforecaster.handler.CommandExecutor;
import ru.liga.currencyforecaster.model.Answer;

@Slf4j
public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final CommandExecutor commandExecutor;

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.commandExecutor = CommandExecutorFactory.getForecastCommandExecutor();
    }

    public static void startBot() {
        final String BOT_TOKEN = System.getenv("BOT_TOKEN");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            botsApi.registerBot(new Bot("CurrencyForecaster", BOT_TOKEN));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Ответ на запрос с командой в строчном формате
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        log.info("Received an update for chatId: {}", chatId);
        Answer answer = commandExecutor.executeCommand(chatId, userName, msg.getText());

        log.debug("Finished processing forecast: isGraph = {}", answer.getIsGraph());
        log.info("Sending answer for chatId: {}", chatId);
        setAnswer(answer);
    }

    /**
     * Формирование имени пользователя
     *
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();

        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     *
     * @param answer Объект с данными по ответу
     */
    private void setAnswer(Answer answer) {
        try {
            if (answer.getIsGraph()) {
                log.debug("Sending graph");
                execute(answer.getPhoto());
            } else {
                log.debug("Sending message");
                execute(answer.getMessage());
            }
            log.info("Successfully sent the answer");
        } catch (TelegramApiException e) {
            log.error("An error occurred while sending the answer: {}", e.getMessage());
        }
    }
}