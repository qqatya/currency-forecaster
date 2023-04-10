package ru.liga.currencyforecaster.factory;

import ru.liga.currencyforecaster.controller.CommandParsingController;
import ru.liga.currencyforecaster.controller.RegressionParsingController;
import ru.liga.currencyforecaster.controller.impl.CommandParsingControllerImpl;
import ru.liga.currencyforecaster.controller.impl.RegressionParsingControllerImpl;

public class ControllerFactory {
    public static CommandParsingController getCommandParsingController() {
        return new CommandParsingControllerImpl();
    }

    public static RegressionParsingController getRegressionParsingController() {
        return new RegressionParsingControllerImpl();
    }
}
