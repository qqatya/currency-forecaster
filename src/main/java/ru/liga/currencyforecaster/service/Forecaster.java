package ru.liga.currencyforecaster.service;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Forecaster {
    public static Answer processForecast(Long chatId, String userName, String command) {
        Command parsedCommand;
        CommandValidator commandValidator = new CommandValidator(command);
        ForecastBuilder forecastBuilder;
        if (commandValidator.getErrorMessage() != null) {
            log.warn("Invalid command. Start building error message");
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(commandValidator.getErrorMessage());
            return new Answer(chatId, userName, message);
        } else {
            log.info("Command is valid");
            parsedCommand = CommandParser.parseCommand(command);
            log.debug("Successfully parsed command: {}", parsedCommand);
            forecastBuilder = new ForecastBuilder(parsedCommand);
        }
        if (parsedCommand.getKeys().containsValue(OutputType.GRAPH.getCommandPart())) {
            log.debug("Building graph");
            return new Answer(chatId, userName, forecastBuilder.getGraph(chatId, parsedCommand.getCurrency()));
        } else {
            log.debug("Building list");
            SendMessage message = new SendMessage();

            message.setText(Printer.printResult(forecastBuilder.createResultRates(parsedCommand.getCurrency())));
            message.setChatId(chatId);
            return new Answer(chatId, userName, message);
        }
    }
}
