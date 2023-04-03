package ru.liga.currencyforecaster.service.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.currencyforecaster.model.Answer;

public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    //Класс для обработки сообщений, не являющихся командой
    private final NonCommand nonCommand;

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        //создаём вспомогательный класс для работы с сообщениями, не являющимися командами
        this.nonCommand = new NonCommand();
        //регистрируем команды
        //register(new StartCommand("start", "Старт"));
    }

    public static void startBot() {
        final String token = "5579562082:AAEY2ZpdxPJg9hPvuiULEnhzTo4v3v0UrXI";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("CurrencyForecaster", token));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String token = "5579562082:AAEY2ZpdxPJg9hPvuiULEnhzTo4v3v0UrXI";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("currencyforecaster_bot", token));
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
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);


        Answer answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
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
     * @param chatId   id чата
     * @param userName имя пользователя
     * @param text     текст ответа
     */
    private void setAnswer(Answer answer) {
        //SendMessage answer = new SendMessage();
        /*answer.setText(text);
        answer.setChatId(chatId.toString());*/
        try {
            System.out.println("send answer");
            if (answer.getGraph()) {
                execute(answer.getPhoto());
            } else {
                execute(answer.getMessage());
            }
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
            e.printStackTrace();
        }
    }
}