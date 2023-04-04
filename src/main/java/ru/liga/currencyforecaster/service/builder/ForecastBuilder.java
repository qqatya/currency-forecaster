package ru.liga.currencyforecaster.service.builder;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class ForecastBuilder {
    private int targetDaysAmount;
    private LocalDate startDate;
    private AlgorithmType algorithmType;

    public ForecastBuilder(Command command) {
        this.startDate = LocalDate.now().plusDays(1);
        this.targetDaysAmount = 1;

        for (Map.Entry<KeyType, String> entry : command.getKeys().entrySet()) {
            if (ForecastRange.findByCommand(entry.getValue()) != ForecastRange.DEF) {
                this.targetDaysAmount = ForecastRange.findByCommand(entry.getValue()).getDay();
            }
            if (entry.getKey() == KeyType.DATE) {
                this.startDate = findStartDate(entry.getValue());
            }
            if (AlgorithmType.findByCommand(entry.getValue()) != AlgorithmType.DEF) {
                this.algorithmType = AlgorithmType.findByCommand(entry.getValue());
            }
        }
    }

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param currencies Валюты из команды
     * @return Список сущностей с прогнозом курсов валют
     */
    public List<Currency> createResultRates(Set<CurrencyType> currencies) {
        List<Currency> forecastPattern = getCurrenciesByType(currencies);

        log.debug("target days = {}", targetDaysAmount);
        ForecastAlgorithm forecastAlgorithm = AlgorithmFactory.getForecastAlgorithm(algorithmType);

        return forecastAlgorithm.predictRateForSomeDays(forecastPattern, startDate,
                targetDaysAmount);
    }

    /**
     * Создание графика в формате изображения
     *
     * @param chatId     Идентификатор чата с ботом
     * @param currencies Валюты из команды
     * @return Изображение графа
     */
    public SendPhoto getGraph(Long chatId, Set<CurrencyType> currencies) {
        List<String> paths = new ArrayList<>();
        List<Currency>[] forecastPattern = new List[currencies.size()];

        for (CurrencyType currency : currencies) {
            paths.add(currency.getFilePath());
        }
        ForecastAlgorithm forecastAlgorithm = AlgorithmFactory.getForecastAlgorithm(algorithmType);

        for (int i = 0; i < paths.size(); i++) {
            List<Currency> temp = CsvParser.parseFile(CsvReader.
                    readAllFromFile(CsvReader.loadFileByPath(paths.get(i))));

            forecastPattern[i] = forecastAlgorithm.predictRateForSomeDays(temp, startDate,
                    targetDaysAmount);
        }
        GraphBuilder graphBuilder = new GraphBuilder("Прогноз", 560, 367, forecastPattern);

        log.debug("Built graph");
        return getGraphPicture(chatId, graphBuilder);
    }

    private LocalDate findStartDate(String value) {
        if (ForecastRange.findByCommand(value) == ForecastRange.TOMORROW) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private List<Currency> getCurrenciesByType(Set<CurrencyType> currencyTypes) {
        String path = "";
        for (CurrencyType currency : currencyTypes) {
            path = currency.getFilePath();
        }
        return CsvParser.parseFile(CsvReader.readAllFromFile(CsvReader.loadFileByPath(path)));
    }

    private SendPhoto getGraphPicture(Long chatId, GraphBuilder graphBuilder) {
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
