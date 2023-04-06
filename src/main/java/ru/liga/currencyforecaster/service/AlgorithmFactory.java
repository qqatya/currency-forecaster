package ru.liga.currencyforecaster.service;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.enums.AlgorithmTypeEnum;
import ru.liga.currencyforecaster.exception.AlgorithmTypeNotFoundException;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmAvg;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmFromInternet;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmLastYear;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmMystical;

/**
 * Поиск алгоритма расчета
 */
@Slf4j
public class AlgorithmFactory {
    public static ForecastAlgorithm getForecastAlgorithm(AlgorithmTypeEnum type) {
        log.debug("Chosen algorithm type: {}", type);
        return switch (type) {
            case MYST -> new ForecastAlgorithmMystical();
            case REGR -> new ForecastAlgorithmFromInternet();
            case LYEAR -> new ForecastAlgorithmLastYear();
            case AVG -> new ForecastAlgorithmAvg();
            case DEF -> throw new AlgorithmTypeNotFoundException();
        };
    }
}