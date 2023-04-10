package ru.liga.currencyforecaster.controller;

import ru.liga.currencyforecaster.model.Currency;

import java.util.List;

public interface FileParsingController {
    /**
     * Парсинг строк, считанных из файла, для создания сущностей, по которым ведется прогноз
     *
     * @param lines Список строк, полученных при чтении файла
     * @return Результат парсинга
     */
    List<Currency> parseFile(List<String> lines);
}
