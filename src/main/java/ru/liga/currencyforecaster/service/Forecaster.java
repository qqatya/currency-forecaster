package ru.liga.currencyforecaster.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.currencyforecaster.controller.CommandParsingController;
import ru.liga.currencyforecaster.enums.OutputTypeEnum;
import ru.liga.currencyforecaster.factory.ControllerFactory;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.service.builder.ForecastBuilder;
import ru.liga.currencyforecaster.utils.Printer;
import ru.liga.currencyforecaster.validation.CommandValidator;

/**
 * Создание прогноза по команде пользователя
 */
@Slf4j
public class Forecaster {
    public static Answer processForecast(Long chatId, String userName, String command) {
        Command parsedCommand;
        CommandValidator commandValidator = new CommandValidator(command);
        ForecastBuilder forecastBuilder;
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        if (commandValidator.getErrorMessage() != null) {
            log.warn("Invalid command. Start building error message");
            message.setText(commandValidator.getErrorMessage());
            return new Answer(chatId, userName, message);
        }
        log.info("Command is valid");
        CommandParsingController commandParsingController = ControllerFactory.getCommandParsingController();
        parsedCommand = commandParsingController.parseCommand(command);

        log.debug("Successfully parsed command: {}", parsedCommand);
        forecastBuilder = new ForecastBuilder(parsedCommand);

        if (parsedCommand.getKeys().containsValue(OutputTypeEnum.GRAPH.getCommandPart())) {
            log.debug("Building graph");
            return new Answer(chatId, userName, forecastBuilder.getGraph(chatId, parsedCommand.getCurrencies()));
        }
        log.debug("Building list");
        message.setText(Printer.printResult(forecastBuilder.createResultRates(parsedCommand.getCurrencies())));
        return new Answer(chatId, userName, message);
    }
}
