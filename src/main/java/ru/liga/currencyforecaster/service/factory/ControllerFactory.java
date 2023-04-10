package ru.liga.currencyforecaster.service.factory;

import ru.liga.currencyforecaster.controller.CommandParsingController;
import ru.liga.currencyforecaster.controller.FileParsingController;
import ru.liga.currencyforecaster.controller.RegressionParsingController;
import ru.liga.currencyforecaster.controller.impl.CommandParsingControllerImpl;
import ru.liga.currencyforecaster.controller.impl.CsvFileParsingControllerImpl;
import ru.liga.currencyforecaster.controller.impl.RegressionParsingControllerImpl;

public class ControllerFactory {
    public static CommandParsingController getCommandParsingController() {
        return new CommandParsingControllerImpl();
    }

    public static FileParsingController getFileParsingController() {
        return new CsvFileParsingControllerImpl();
    }

    public static RegressionParsingController getRegressionParsingController() {
        return new RegressionParsingControllerImpl();
    }
}
