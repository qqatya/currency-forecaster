package ru.liga.currencyforecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.liga.currencyforecaster.enums.CommandEnum;
import ru.liga.currencyforecaster.enums.CurrencyTypeEnum;
import ru.liga.currencyforecaster.enums.KeyEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс для хранения команды
 */
@AllArgsConstructor
@Getter
@ToString
public class Command {
    private final CommandEnum type;
    private final Set<CurrencyTypeEnum> currencies;
    private final Map<KeyEnum, String> keys;

    public static Command getDefaultCommand() {
        Set<CurrencyTypeEnum> tempCur = new HashSet<>();
        Map<KeyEnum, String> tempKeys = new HashMap();

        tempCur.add(CurrencyTypeEnum.DEF);
        tempKeys.put(KeyEnum.DEF, "");
        return new Command(CommandEnum.DEF, tempCur, tempKeys);
    }
}
