package ru.liga.currencyforecaster.service.builder;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.currencyforecaster.enums.AlgorithmTypeEnum;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.enums.ForecastRangeEnum;
import ru.liga.currencyforecaster.enums.KeyEnum;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.factory.AlgorithmFactory;
import ru.liga.currencyforecaster.factory.CurrencyMapperFactory;
import ru.liga.currencyforecaster.mapper.CurrencyMapper;
import ru.liga.currencyforecaster.model.Command;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;
import ru.liga.currencyforecaster.utils.CsvReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.liga.currencyforecaster.enums.MessageEnum.EMPTY_SET;

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

        return forecastAlgorithm.predictRate(forecastPattern, startDate,
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
        if (currencies.isEmpty()) {
            throw new EmptyObjectException(EMPTY_SET.getMessage());
        }
        List<CurrencyTypeEnum> tempCur = new ArrayList<>(currencies);
        List<Currency>[] forecastPattern = new List[currencies.size()];

        ForecastAlgorithm forecastAlgorithm = AlgorithmFactory.getForecastAlgorithm(algorithmType);
        CurrencyMapper currencyMapper = CurrencyMapperFactory.getCurrencyMapper();

        for (int i = 0; i < tempCur.size(); i++) {
            List<Currency> temp = currencyMapper.parseFile(CsvReader.
                    readAllFromFile(tempCur.get(i).getPath()));

            forecastPattern[i] = forecastAlgorithm.predictRate(temp, startDate,
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
        CurrencyMapper currencyMapper = CurrencyMapperFactory.getCurrencyMapper();

        for (CurrencyTypeEnum currency : currencyTypeEnums) {
            fileName = currency.getPath();
        }
        return currencyMapper.parseFile(CsvReader.readAllFromFile(fileName));
    }

    private SendPhoto getGraphPicture(Long chatId, GraphBuilder graphBuilder) {
        SendPhoto message = new SendPhoto();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(out,
                    graphBuilder.getXYLineChart(),
                    graphBuilder.getWidth(),
                    graphBuilder.getHeight());
            byte[] bytes = out.toByteArray();
            try (InputStream in = new ByteArrayInputStream(bytes)) {
                message.setPhoto(new InputFile(in, "forecast.png"));
            }
        } catch (IOException ex) {
            log.error("An error occurred while creating a picture for chatId: {}", chatId);
        }
        message.setChatId(chatId);
        return message;
    }
}
