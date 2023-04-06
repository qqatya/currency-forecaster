package ru.liga.currencyforecaster.service.builder;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.currencyforecaster.controller.CsvParsingController;
import ru.liga.currencyforecaster.enums.AlgorithmTypeEnum;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.enums.ForecastRangeEnum;
import ru.liga.currencyforecaster.enums.KeyEnum;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.AlgorithmFactory;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;
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
    /**
     * Значение для расчета по умолчанию
     */
    private static final int DEF_VALUE = 1;
    private int targetDaysAmount;
    private LocalDate startDate;
    private AlgorithmTypeEnum algorithmType;

    public ForecastBuilder(Command command) {
        this.startDate = LocalDate.now().plusDays(DEF_VALUE);
        this.targetDaysAmount = DEF_VALUE;

        for (Map.Entry<KeyEnum, String> entry : command.getKeys().entrySet()) {
            if (ForecastRangeEnum.findByCommand(entry.getValue()) != ForecastRangeEnum.DEF) {
                this.targetDaysAmount = ForecastRangeEnum.findByCommand(entry.getValue()).getDay();
            }
            if (entry.getKey() == KeyEnum.DATE) {
                this.startDate = findStartDate(entry.getValue());
            }
            if (AlgorithmTypeEnum.findByCommand(entry.getValue()) != AlgorithmTypeEnum.DEF) {
                this.algorithmType = AlgorithmTypeEnum.findByCommand(entry.getValue());
            }
        }
    }

    /**
     * Создание списка сущностей с прогнозом курсов валют по типу валюты и количеству дней для прогноза
     *
     * @param currencies Валюты из команды
     * @return Список сущностей с прогнозом курсов валют
     */
    public List<Currency> createResultRates(Set<CurrencyTypeEnum> currencies) {
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
    public SendPhoto getGraph(Long chatId, Set<CurrencyTypeEnum> currencies) {
        List<String> fileNames = new ArrayList<>();
        List<Currency>[] forecastPattern = new List[currencies.size()];

        for (CurrencyTypeEnum currency : currencies) {
            fileNames.add(currency.getPath());
        }
        ForecastAlgorithm forecastAlgorithm = AlgorithmFactory.getForecastAlgorithm(algorithmType);

        for (int i = 0; i < fileNames.size(); i++) {
            List<Currency> temp = CsvParsingController.parseFile(CsvReader.
                    readAllFromFile(CsvReader.getFilePath(fileNames.get(i))));

            forecastPattern[i] = forecastAlgorithm.predictRateForSomeDays(temp, startDate,
                    targetDaysAmount);
        }
        int pngWidth = 560;
        int pngHeight = 367;
        GraphBuilder graphBuilder = new GraphBuilder("Прогноз", pngWidth, pngHeight, forecastPattern);

        log.debug("Built graph");
        return getGraphPicture(chatId, graphBuilder);
    }

    private LocalDate findStartDate(String value) {
        if (ForecastRangeEnum.findByCommand(value) == ForecastRangeEnum.TOMORROW) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private List<Currency> getCurrenciesByType(Set<CurrencyTypeEnum> currencyTypeEnums) {
        String fileName = "";
        for (CurrencyTypeEnum currency : currencyTypeEnums) {
            fileName = currency.getPath();
        }
        return CsvParsingController.parseFile(CsvReader.readAllFromFile(CsvReader.getFilePath(fileName)));
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        message.setChatId(chatId);
        return message;
    }
}
