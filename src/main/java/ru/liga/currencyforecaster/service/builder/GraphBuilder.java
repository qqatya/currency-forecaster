package ru.liga.currencyforecaster.service.builder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.liga.currencyforecaster.model.Currency;

import java.util.List;

/**
 * Класс для создания графиков
 */
@Slf4j
@Getter
public class GraphBuilder {
    /**
     * Индекс объекта Currency, созданного из самой новой записи в csv-файле
     */
    private static final int NEWEST_CURRENCY_INDEX = 0;
    private final JFreeChart xYLineChart;
    private final String title;
    private final int width;
    private final int height;

    public GraphBuilder(String title, int width, int height, List<Currency>... currencies) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.xYLineChart = ChartFactory.createXYLineChart(
                title,
                "Дни",
                "Курс",
                createDataset(currencies));
        log.debug("Built XYchart for graph");
    }

    private XYSeries createXYSeries(List<Currency> currencies) {
        XYSeries values = new XYSeries(currencies.get(NEWEST_CURRENCY_INDEX).getCurrencyType().getCurrencyName());

        for (int i = 1; i <= currencies.size(); i++) {
            values.add(i, currencies.get(i - 1).getRate());
        }
        return values;
    }

    private XYDataset createDataset(List<Currency>... currencies) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (List<Currency> currency : currencies) {
            dataset.addSeries(createXYSeries(currency));
        }
        return dataset;
    }
}
