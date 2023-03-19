package ru.liga;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyForecaster {
    private static double predictRateForNextDay(List<CurrencyDto> currencyDtos) {
        double rateSum = 0;

        for (CurrencyDto currencyDto : currencyDtos) {
            rateSum += currencyDto.getRate();
        }
        return rateSum / currencyDtos.size();
    }

    public static List<CurrencyDto> predictRateForSomeDays(List<CurrencyDto> currencyDtos, int daysAmount) {
        List<CurrencyDto> ratesResult = new ArrayList<>();

        for (int i = 1; i <= daysAmount; i++) {
            double predictedRate = predictRateForNextDay(currencyDtos);
            int nominal = currencyDtos.get(0).getNominal();
            LocalDate date = LocalDate.now().plusDays(i);
            String currencyName = currencyDtos.get(0).getCurrencyName();
            CurrencyDto currencyDto = new CurrencyDto(nominal, date, predictedRate, currencyName);

            ratesResult.add(currencyDto);
            currencyDtos.remove(0);
            currencyDtos.add(currencyDto);
        }
        return ratesResult;
    }


}
