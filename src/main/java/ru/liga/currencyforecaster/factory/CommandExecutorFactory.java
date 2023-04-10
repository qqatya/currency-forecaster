package ru.liga.currencyforecaster.factory;

import ru.liga.currencyforecaster.handler.CommandExecutor;
import ru.liga.currencyforecaster.handler.impl.ForecastCommandExecutor;

public class CommandExecutorFactory {
    public static CommandExecutor getCommandExecutor() {
        return new ForecastCommandExecutor();
    }
}
