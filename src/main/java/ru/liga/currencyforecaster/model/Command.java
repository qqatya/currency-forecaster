package ru.liga.currencyforecaster.model;

import ru.liga.currencyforecaster.model.type.CommandType;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.model.type.ForecastRange;

public class Command {
    private final CommandType type;
    private final CurrencyType currency;
    private final ForecastRange range;

    public Command(CommandType type, CurrencyType currency, ForecastRange range) {
        this.type = type;
        this.currency = currency;
        this.range = range;
    }

    public CommandType getType() {
        return type;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public ForecastRange getRange() {
        return range;
    }
}
