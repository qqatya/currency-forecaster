package ru.liga.currencyforecaster.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.liga.currencyforecaster.exception.ValidationException;
import ru.liga.currencyforecaster.factory.CurrencyMapperFactory;
import ru.liga.currencyforecaster.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.liga.currencyforecaster.enums.CurrencyTypeEnum.EUR;

class CurrencyMapperImplTest {

    private static List<String> values;
    private static CurrencyMapper controller;

    @BeforeAll
    static void setUp() {
        controller = CurrencyMapperFactory.getCurrencyMapper();
        values = new ArrayList<>();
        values.add("curs;cdx;nominal;data");
        values.add("82,7750;Евро;1;29.03.2023");
        values.add("82,2913;Евро;1;28.03.2023");
    }

    @Test
    void parsesCurrenciesBasedOnColumns() {
        List<Currency> currencies = controller.parseFile(values);
        Currency currency = new Currency(1,
                LocalDate.of(2023, 3, 29),
                new BigDecimal("82.7750"),
                EUR);

        assertNotNull(currencies);
        assertEquals(currency.toString(), currencies.get(0).toString());
        assertEquals(2, currencies.size());
    }

    @Test
    void throwsExceptionIfColumnsDoNotMatch() {
        values.remove(0);
        values.add(0, "curs;cdx;aaa;data");
        assertThrows(ValidationException.class, () -> controller.parseFile(values));
    }
}