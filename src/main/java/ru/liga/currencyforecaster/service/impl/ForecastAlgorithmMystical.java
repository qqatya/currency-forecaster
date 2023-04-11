package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

import static ru.liga.currencyforecaster.enums.MessageEnum.EMPTY_LIST;

@Slf4j
public class ForecastAlgorithmMystical implements ForecastAlgorithm {
    /**
     * Индекс объекта Currency, созданного из самой новой записи в csv-файле
     */
    private static final int NEWEST_CURRENCY_INDEX = 0;

    @Override
    public List<Currency> predictRate(List<Currency> currencies,
                                      LocalDate startDate,
                                      int daysAmount) {
        if (currencies.isEmpty()) {
            throw new EmptyObjectException(EMPTY_LIST.getMessage());
        }
        List<Currency> tmpCurrencies = new ArrayList<>(currencies);
        List<Currency> ratesResult = new ArrayList<>();
        Set<MonthDay> monthDays = new HashSet<>();
        Map<LocalDate, Currency> currenciesByDate = new HashMap<>();

        for (Currency currency : tmpCurrencies) {
            monthDays.add(MonthDay.of(currency.getDate().getMonth(), currency.getDate().getDayOfMonth()));
            currenciesByDate.put(currency.getDate(), currency);
        }

        for (int i = 0; i < daysAmount; i++) {
            LocalDate rateDate = startDate.plusDays(i);
            if (!ifDateExists(monthDays, rateDate)) {
                log.warn("Date {} does not exist", rateDate);
                continue;
            }
            Currency lastYearRete = predictRateForNextDay(currenciesByDate, tmpCurrencies, rateDate);
            Currency newRate = new Currency(lastYearRete.getNominal(),
                    rateDate,
                    lastYearRete.getRate(),
                    lastYearRete.getCurrencyType());

            ratesResult.add(newRate);

        }
        log.debug("Built mist rates");
        return ratesResult;
    }

    private Currency predictRateForNextDay(Map<LocalDate, Currency> currenciesByDate,
                                           List<Currency> currencies,
                                           LocalDate date) {
        Random random = new Random();
        LocalDate tempDate;
        do {
            int yearsAmount = random.nextInt(findYearsAmount(currencies));
            tempDate = LocalDate.of(date.minusYears(yearsAmount).getYear(),
                    date.getMonth(),
                    date.getDayOfMonth());
        } while (!currenciesByDate.containsKey(tempDate));
        return currenciesByDate.get(tempDate);
    }

    private boolean ifDateExists(Set<MonthDay> monthDays, LocalDate date) {
        return monthDays.contains(MonthDay.of(date.getMonth(), date.getDayOfMonth()));
    }

    private int findYearsAmount(List<Currency> currencies) {
        int firstYear = currencies.get(NEWEST_CURRENCY_INDEX).getDate().getYear();
        int lastYear = currencies.get(currencies.size() - 1).getDate().getYear();

        return firstYear - lastYear;
    }
}
