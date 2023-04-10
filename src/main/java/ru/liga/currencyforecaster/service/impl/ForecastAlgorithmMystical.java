package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.exception.EmptyObjectException;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
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

        for (int i = 0; i < daysAmount; i++) {
            LocalDate rateDate = startDate.plusDays(i);
            if (!ifDateExists(tmpCurrencies, rateDate)) {
                log.warn("Date {} does not exist", rateDate);
                continue;
            }
            Currency lastYearRete = predictRateForNextDay(tmpCurrencies, rateDate);
            Currency newRate = new Currency(lastYearRete.getNominal(),
                    rateDate,
                    lastYearRete.getRate(),
                    lastYearRete.getCurrencyType());

            ratesResult.add(newRate);

        }
        log.debug("Built mist rates");
        return ratesResult;
    }

    private Currency predictRateForNextDay(List<Currency> currencies, LocalDate date) {
        Map<LocalDate, Currency> tempCur = new HashMap<>();
        Random random = new Random();
        LocalDate tempDate;

        for (Currency currency : currencies) {
            tempCur.put(currency.getDate(), currency);
        }
        do {
            int yearsAmount = random.nextInt(findYearsAmount(currencies));
            tempDate = LocalDate.of(date.minusYears(yearsAmount).getYear(),
                    date.getMonth(),
                    date.getDayOfMonth());
        } while (!tempCur.containsKey(tempDate));
        return tempCur.get(tempDate);
    }

    private int findYearsAmount(List<Currency> currencies) {
        int firstYear = currencies.get(NEWEST_CURRENCY_INDEX).getDate().getYear();
        int lastYear = currencies.get(currencies.size() - 1).getDate().getYear();

        return firstYear - lastYear;
    }

    private boolean ifDateExists(List<Currency> currencies, LocalDate date) {
        for (Currency currency : currencies) {
            if (date.getMonth().equals(currency.getDate().getMonth())
                    && date.getDayOfMonth() == currency.getDate().getDayOfMonth()) {
                return true;
            }
        }
        return false;
    }
}
