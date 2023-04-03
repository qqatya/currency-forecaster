package ru.liga.currencyforecaster.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.type.OutputType;
import ru.liga.currencyforecaster.service.builder.ForecastBuilder;
import ru.liga.currencyforecaster.service.parser.CommandParser;
import ru.liga.currencyforecaster.service.validator.CommandValidator;
import ru.liga.currencyforecaster.utils.Printer;

/**
 * Создание прогноза по команде пользователя
 */
public class Forecaster {
    public static Answer processForecast(Long chatId, String userName, String command) {
        Command parsedCommand;
        CommandValidator commandValidator = new CommandValidator(command);

        if (commandValidator.getErrorMessage() != null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(commandValidator.getErrorMessage());
            return new Answer(chatId, userName, message);
        } else {
            parsedCommand = CommandParser.parseCommand(command);
        }
        if (parsedCommand.getKeys().containsValue(OutputType.GRAPH.getCommandPart())) {
            return new Answer(chatId, userName, ForecastBuilder.getGraph(chatId, parsedCommand));
        } else {
            SendMessage message = new SendMessage();
            message.setText(Printer.printResult(ForecastBuilder.createResultRates(parsedCommand)));
            message.setChatId(chatId);
            return new Answer(chatId, userName, message);
        }
    }
}
