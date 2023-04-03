package ru.liga.currencyforecaster.service;

import ru.liga.currencyforecaster.model.type.AlgorithmType;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmAvg;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmFromInternet;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmLastYear;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmMystical;

public class AlgorithmSearcher {
    public ForecastAlgorithm getForecastAlgorithm(AlgorithmType type) {
        switch (type) {
            case AVG -> {
                return new ForecastAlgorithmAvg();
            }
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
                return null;
            }
        }
    }
}