package ru.liga.currencyforecaster.service;

import org.jfree.chart.ChartUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.currencyforecaster.model.Answer;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.*;
import ru.liga.currencyforecaster.service.parser.CommandParser;
import ru.liga.currencyforecaster.service.parser.CsvParser;
import ru.liga.currencyforecaster.service.validator.CommandValidator;
import ru.liga.currencyforecaster.utils.ConsolePrinter;
import ru.liga.currencyforecaster.utils.CsvReader;
import ru.liga.currencyforecaster.utils.GraphBuilder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Forecaster {


    public static Answer processForecast(Long chatId, String userName, String command) {
        Command parsedCommand;
        //Answer answer = new Answer();

        /*System.out.print(ConsolePrinter.printCurrencies());
        System.out.print(ConsolePrinter.printCommand());*/
        CommandValidator commandValidator = new CommandValidator(command);

        if (commandValidator.getErrorMessage() != null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(commandValidator.getErrorMessage());
            return new Answer(chatId, userName, message);
            //parsedCommand = Command.getDefaultCommand();
        } else {
            parsedCommand = CommandParser.parseCommand(command);
        }
        if (parsedCommand.getKeys().containsValue(OutputType.GRAPH.getCommandPart())) {
            return new Answer(chatId, userName, getGraph(chatId, parsedCommand));
        } else {
            SendMessage message = new SendMessage();
            message.setText(ConsolePrinter.printResult(createResultRates(parsedCommand)));
            message.setChatId(chatId);
            return new Answer(chatId, userName, message);
        }
    }

    /*public static void start() {
        final String token = "5579562082:AAEY2ZpdxPJg9hPvuiULEnhzTo4v3v0UrXI";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("CurrencyForecaster", token));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String command;
        Command parsedCommand;

        System.out.print(ConsolePrinter.printCurrencies());
        System.out.print(ConsolePrinter.printCommand());
        do {
            System.out.println(ENTER_COMMAND.getMessage());
            command = scanner.nextLine();
            CommandValidator commandValidator = new CommandValidator(command);

            if (commandValidator.getErrorMessage() != null) {
                System.out.println(commandValidator.getErrorMessage());
                parsedCommand = Command.getDefaultCommand();
            } else {
                parsedCommand = CommandParser.parseCommand(command);
            }
            if (!parsedCommand.getCurrency().contains(CurrencyType.DEF)) {
                System.out.print(ConsolePrinter.printResult(createResultRates(parsedCommand)));
            }
            getGraph(parsedCommand);
        } while (CommandType.findByCommand(command) != CommandType.Q);
    }*/

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param command Команда пользователя
     * @return Список сущностей с прогнозом курсов валют
     */
    private static List<Currency> createResultRates(Command command) {
        int targetDaysAmount = 1;
        LocalDate startDate = LocalDate.now().plusDays(1);
        String path = null;
        Map<KeyType, String> keys = command.getKeys();
        AlgorithmType algorithmType = AlgorithmType.DEF;

        for (Map.Entry<KeyType, String> entry : keys.entrySet()) {
            if (ForecastRange.findByCommand(entry.getValue()) != ForecastRange.DEF) {
                targetDaysAmount = ForecastRange.findByCommand(entry.getValue()).getDay();
            }
            if (entry.getKey() == KeyType.DATE) {
                startDate = findStartDate(entry.getValue());
            }
            if (AlgorithmType.findByCommand(entry.getValue()) != AlgorithmType.DEF) {
                algorithmType = AlgorithmType.findByCommand(entry.getValue());
            }
        }
        for (CurrencyType currency : command.getCurrency()) {
            path = currency.getFilePath();
        }
        List<Currency> currencies = CsvParser.parseFile(CsvReader.
                readAllFromFile(CsvReader.loadFileByPath(path)));

        AlgorithmSearcher searcher = new AlgorithmSearcher();
        ForecastAlgorithm forecastAlgorithm = searcher.getForecastAlgorithm(algorithmType);
        return forecastAlgorithm.predictRateForSomeDays(currencies, startDate,
                targetDaysAmount);
    }

    private static LocalDate findStartDate(String value) {
        if (ForecastRange.findByCommand(value) == ForecastRange.TOMORROW) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private static SendPhoto getGraph(Long chatId, Command command) {
        List<String> paths = new ArrayList<>();
        List<Currency>[] currencies = new List[command.getCurrency().size()];

        for (CurrencyType currency : command.getCurrency()) {
            paths.add(currency.getFilePath());
        }

        for (int i = 0; i < paths.size(); i++) {
            currencies[i] = CsvParser.parseFile(CsvReader.
                    readFromFile(CsvReader.loadFileByPath(paths.get(i)),
                            ForecastRange.findByCommand(command.getKeys().get(KeyType.PERIOD)).getDay()));
        }

        GraphBuilder graphBuilder = new GraphBuilder("Прогноз", 560, 367, currencies);

        try (OutputStream out = new FileOutputStream("forecast.jpg")) {
            ChartUtils.writeChartAsPNG(out,
                    graphBuilder.getXYLineChart(),
                    graphBuilder.getWidth(),
                    graphBuilder.getHeight());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SendPhoto message = new SendPhoto();
        message.setPhoto(new InputFile(new File("forecast.jpg")));
        message.setChatId(chatId);
        return message;
    }
}
