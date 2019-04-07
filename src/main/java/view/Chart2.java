package view;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Chart2 extends JPanel{

    JFreeChart freeChart;

    public Chart2(int[] matches, String nom){
        XYSeries series = new XYSeries("A");
        for (int i = 0; i < matches.length; i++)
            series.add(i, matches[i]);
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        freeChart = ChartFactory.createXYLineChart(nom, null, null, dataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(freeChart);
        chartPanel.setDomainZoomable(true);

        this.add(chartPanel);

        update(matches, nom);


    }


    void update(int[] matches, String nom){
        XYSeries series = new XYSeries(nom);
        for (int i = 0; i < matches.length; i++)
            series.add(i, matches[i]);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        freeChart.getXYPlot().setDataset(dataset);
    }
}
