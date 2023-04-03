package ru.liga.currencyforecaster;

import ru.liga.currencyforecaster.service.Forecaster;
import ru.liga.currencyforecaster.service.telegram.Bot;

public class CurrencyForecasterApp {
    public static void main(String[] args) {
        Bot.startBot();
    }
}