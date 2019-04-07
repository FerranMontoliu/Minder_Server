package view;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class JAccepted extends JPanel {

    public JAccepted(int acceptats, int vistos){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Acceptats", acceptats);
        dataset.setValue("Vistos", vistos-acceptats);

        JFreeChart someChart = ChartFactory.createPieChart(
                "Acceptats/Visualitzats", dataset, true, true, false);
        PiePlot plot = (PiePlot) someChart.getPlot();
        plot.setSectionPaint("Acceptats", new Color(220,20,60));
        plot.setSectionPaint("Vistos", new Color(255,160,122) );
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%"));
        plot.setLabelGenerator(gen);

        this.add(new ChartPanel(someChart));
    }

}
