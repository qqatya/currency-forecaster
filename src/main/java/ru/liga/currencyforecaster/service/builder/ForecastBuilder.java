package ru.liga.currencyforecaster.service.builder;

import org.jfree.chart.ChartUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.AlgorithmType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;
import ru.liga.currencyforecaster.model.type.KeyType;
import ru.liga.currencyforecaster.service.AlgorithmFactory;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;
import ru.liga.currencyforecaster.service.parser.CsvParser;
import ru.liga.currencyforecaster.utils.CsvReader;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForecastBuilder {
    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param command Команда пользователя
     * @return Список сущностей с прогнозом курсов валют
     */
    public static List<Currency> createResultRates(Command command) {
        int targetDaysAmount = 1;
        LocalDate startDate = LocalDate.now().plusDays(1);
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
        List<Currency> currencies = getCurrenciesByType(command.getCurrency());
        ForecastAlgorithm forecastAlgorithm = AlgorithmFactory.getForecastAlgorithm(algorithmType);

        return forecastAlgorithm.predictRateForSomeDays(currencies, startDate,
                targetDaysAmount);
    }

    /**
     * Создание графика в формате изображения
     *
     * @param chatId  Идентификатор чата с ботом
     * @param command Введенная команда
     * @return Изображение графа
     */
    public static SendPhoto getGraph(Long chatId, Command command) {
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

        return getGraphPicture(chatId, graphBuilder);
    }

    private static LocalDate findStartDate(String value) {
        if (ForecastRange.findByCommand(value) == ForecastRange.TOMORROW) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private static List<Currency> getCurrenciesByType(Set<CurrencyType> currencyTypes) {
        String path = "";
        for (CurrencyType currency : currencyTypes) {
            path = currency.getFilePath();
        }
        return CsvParser.parseFile(CsvReader.readAllFromFile(CsvReader.loadFileByPath(path)));
    }

    private static SendPhoto getGraphPicture(Long chatId, GraphBuilder graphBuilder) {
        SendPhoto message = new SendPhoto();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(out,
                    graphBuilder.getXYLineChart(),
                    graphBuilder.getWidth(),
                    graphBuilder.getHeight());
            byte[] bytes = out.toByteArray();
            InputStream in = new ByteArrayInputStream(bytes);
            message.setPhoto(new InputFile(in, "forecast.png"));
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        message.setChatId(chatId);
        return message;
    }
}
