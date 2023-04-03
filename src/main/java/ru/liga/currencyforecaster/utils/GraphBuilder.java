package ru.liga.currencyforecaster.utils;

import java.awt.Color;
import java.awt.BasicStroke;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ChartUtils;
import ru.liga.currencyforecaster.model.Currency;
import ru.liga.currencyforecaster.model.type.CurrencyType;

public class GraphBuilder {
    private final JFreeChart xYLineChart;
    private final String title;
    private final int width;
    private final int height;

    public GraphBuilder(String title, int width, int height, List<Currency> ... currencies) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.xYLineChart = ChartFactory.createXYLineChart(
                title,
                "Дни",
                "Курс",
                createDataset(currencies));
    }

    public JFreeChart getXYLineChart() {
        return xYLineChart;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private XYSeries createXYSeries(List<Currency> currencies) {
        XYSeries values = new XYSeries(currencies.get(0).getCurrencyType().getCurrencyName());

        for (int i = 1; i <= currencies.size(); i++) {
            values.add(i, currencies.get(i-1).getRate());
        }
        return values;
    }

    private XYDataset createDataset(List<Currency> ... currencies) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (List<Currency> currency : currencies) {
            dataset.addSeries(createXYSeries(currency));
        }
        return dataset;



        /*final XYSeries firefox = new XYSeries("Firefox");
        firefox.add(1.0, 1.0);
        firefox.add(2.0, 4.0);
        firefox.add(3.0, 3.0);

        final XYSeries chrome = new XYSeries("Chrome");
        chrome.add(1.0, 4.0);
        chrome.add(2.0, 5.0);
        chrome.add(3.0, 6.0);

        final XYSeries iexplorer = new XYSeries("InternetExplorer");
        iexplorer.add(3.0, 4.0);
        iexplorer.add(4.0, 5.0);
        iexplorer.add(5.0, 4.0);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(firefox);
        dataset.addSeries(chrome);
        dataset.addSeries(iexplorer);*/
    }

    /*public static void main( String[ ] args ) {
        //GraphBuilder chart = new GraphBuilder("Browser Usage Statistics");
        //chart.pack( );
        //chart.setVisible( true );
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "lol",
                "Range",
                "Rate",
                GraphBuilder.createDataset());

        try {

            OutputStream out = new FileOutputStream("chart.png");
            ChartUtils.writeChartAsPNG(out,
                    xylineChart,
                    560,
                    367);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/
}
