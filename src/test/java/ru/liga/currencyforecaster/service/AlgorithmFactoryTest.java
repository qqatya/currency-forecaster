package ru.liga.currencyforecaster.service;

import org.junit.jupiter.api.Test;
import ru.liga.currencyforecaster.model.type.AlgorithmType;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmAvg;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmFromInternet;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmLastYear;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmMystical;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmFactoryTest {
    @Test
    public void returnsForecastAlgorithmMysticalIfAlgorithmTypeIsMyst() {
        assertInstanceOf(ForecastAlgorithmMystical.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmType.MYST));
    }

    @Test
    public void returnsForecastAlgorithmLastYearIfAlgorithmTypeIsLyear() {
        assertInstanceOf(ForecastAlgorithmLastYear.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmType.LYEAR));
    }

    @Test
    public void returnsForecastAlgorithmFromInternetIfAlgorithmTypeIsRegr() {
        assertInstanceOf(ForecastAlgorithmFromInternet.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmType.REGR));
    }

    @Test
    public void returnsForecastAlgorithmAvgIfAlgorithmTypeIsAvg() {
        assertInstanceOf(ForecastAlgorithmAvg.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmType.AVG));
    }
}