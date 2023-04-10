package ru.liga.currencyforecaster.service;

import org.junit.jupiter.api.Test;
import ru.liga.currencyforecaster.enums.AlgorithmTypeEnum;
import ru.liga.currencyforecaster.factory.AlgorithmFactory;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmAvg;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmFromInternet;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmLastYear;
import ru.liga.currencyforecaster.service.impl.ForecastAlgorithmMystical;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmFactoryTest {
    @Test
    public void returnsForecastAlgorithmMysticalIfAlgorithmTypeIsMyst() {
        assertInstanceOf(ForecastAlgorithmMystical.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmTypeEnum.MYST));
    }

    @Test
    public void returnsForecastAlgorithmLastYearIfAlgorithmTypeIsLyear() {
        assertInstanceOf(ForecastAlgorithmLastYear.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmTypeEnum.LYEAR));
    }

    @Test
    public void returnsForecastAlgorithmFromInternetIfAlgorithmTypeIsRegr() {
        assertInstanceOf(ForecastAlgorithmFromInternet.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmTypeEnum.REGR));
    }

    @Test
    public void returnsForecastAlgorithmAvgIfAlgorithmTypeIsAvg() {
        assertInstanceOf(ForecastAlgorithmAvg.class,
                AlgorithmFactory.getForecastAlgorithm(AlgorithmTypeEnum.AVG));
    }
}