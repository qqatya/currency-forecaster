package ru.liga.currency_forecaster.utils;

import ru.liga.currency_forecaster.CurrencyForecasterApp;
import ru.liga.currency_forecaster.model.CurrencyType;

import java.io.InputStream;

public class FileSearcher {

    /**
     * Поиск файла со статистикой по типу валюты
     *
     * @param currencyType Тип валюты
     * @return Потоковое представление файла
     */
    public static InputStream findFileByCurrency(CurrencyType currencyType) {
        return CurrencyForecasterApp.class.getClassLoader().getResourceAsStream(currencyType.getFilePath());
    }
}
