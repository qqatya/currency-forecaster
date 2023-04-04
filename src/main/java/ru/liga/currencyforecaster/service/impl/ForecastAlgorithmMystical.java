package ru.liga.currencyforecaster.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;
import ru.liga.currencyforecaster.service.ForecastAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class ForecastAlgorithmMystical implements ForecastAlgorithm {
    @Override
    public List<Currency> predictRateForSomeDays(List<Currency> currencies,
                                                 LocalDate startDate,
                                                 int daysAmount) {
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
        Currency temp;
        Random random = new Random();

        do {
            int yearsAmount = random.nextInt(findYearsAmount(currencies));

            temp = new Currency(1, date.minusYears(yearsAmount),
                    null, CurrencyType.DEF);
        } while (!currencies.contains(temp));
        return currencies.get(currencies.indexOf(temp));
    }

    private int findYearsAmount(List<Currency> currencies) {
        int firstYear = currencies.get(0).getDate().getYear();
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
