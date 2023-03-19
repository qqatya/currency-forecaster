package ru.liga.currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyForecaster {
    /**
     * Расчет прогноза на следующий день
     * @param currencyDtos Список сущностей, по которым ведется расчет
     * @return Спрогнозированный курс
     */
    private static double predictRateForNextDay(List<CurrencyDto> currencyDtos) {
        double rateSum = 0;

        for (CurrencyDto currencyDto : currencyDtos) {
            rateSum += currencyDto.getRate();
        }
        return rateSum / currencyDtos.size();
    }

    /**
     * Расчет прогноза на N дней
     * @param currencyDtos Список сущностей, по которым ведется расчет
     * @param daysAmount Количество дней, на которые нужно рассчитать курс
     * @return Результат прогноза
     */
    public static List<CurrencyDto> predictRateForSomeDays(List<CurrencyDto> currencyDtos, int daysAmount) {
        List<CurrencyDto> tmpCurrencyDtos = new ArrayList<>(currencyDtos);
        List<CurrencyDto> ratesResult = new ArrayList<>();

        for (int i = 1; i <= daysAmount; i++) {
            double predictedRate = predictRateForNextDay(tmpCurrencyDtos);
            int nominal = tmpCurrencyDtos.get(0).getNominal();
            LocalDate date = LocalDate.now().plusDays(i);
            String currencyName = tmpCurrencyDtos.get(0).getCurrencyName();
            CurrencyDto currencyDto = new CurrencyDto(nominal, date, predictedRate, currencyName);

            ratesResult.add(currencyDto);
            tmpCurrencyDtos.remove(0);
            tmpCurrencyDtos.add(currencyDto);
        }
        return ratesResult;
    }
}
