package ru.liga.currencyforecaster.service;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.model.type.AlgorithmType;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmAvg;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmFromInternet;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmLastYear;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmMystical;

/**
 * Поиск алгоритма расчета
 */
@Slf4j
public class AlgorithmFactory {
    public static ForecastAlgorithm getForecastAlgorithm(AlgorithmType type) {
        log.debug("Chosen algorithm type: {}", type);
        switch (type) {
            case MYST -> {
                return new ForecastAlgorithmMystical();
            }
            case REGR -> {
                return new ForecastAlgorithmFromInternet();
            }
            case LYEAR -> {
                return new ForecastAlgorithmLastYear();
            }
            default -> {
                return new ForecastAlgorithmAvg();
            }
        }
    }
}