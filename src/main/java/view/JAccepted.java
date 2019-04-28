package view;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.text.DecimalFormat;


/**
 * Classe que implementa la primera gràfica del servidor (Accepted/Viewed)
 * TODO: Fer que es vagi redibuixant la gràfica en funció dels valors updated de la bbdd -> cal cridar funció redrawGraphs(...)
 */
public class JAccepted extends JPanel {

    private JTextPane jtpane;
    private JTextPane jtpane2;
    private BaarChart barChart;
    private JPanel jpBarres, jpQuesito;
    private int viewed; //No cal realment ?
    private int accepted;

    public JAccepted(int acceptats, int vistos) {

        this.setLayout(new BorderLayout());

        jtpane = new JTextPane();
        jtpane2 = new JTextPane();

        jpQuesito = new JPanel();
        jpBarres = new JPanel();

        barChart = new BaarChart();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Acceptats", acceptats);
        dataset.setValue("Vistos", vistos-acceptats);

        JFreeChart someChart = ChartFactory.createPieChart(null, dataset, true, true, false);
        someChart.getPlot().setBackgroundPaint( Color.WHITE );
        PiePlot plot = (PiePlot) someChart.getPlot();
        plot.setSectionPaint("Acceptats", new Color(220,20,60));
        plot.setSectionPaint("Vistos", new Color(255,160,122));
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%"));
        plot.setLabelGenerator(gen);


        String a = "           ";
        jpQuesito.add(new ChartPanel(someChart));


        JLabel jlabel = new JLabel(a+a+a+a+a+a+"Accepted / Viewed Users Graph Bar");
        Font f = jlabel.getFont();

        this.redrawGraphs(13,25); //TODO: Viewed, Accepted PER DEFECTE

        StyledDocument doc = (StyledDocument) jtpane.getDocument();
        jtpane.setOpaque(false);
        jtpane.setEditable(false);
        Style style = doc.addStyle("StyleName", null);
        StyleConstants.setIcon(style, new ImageIcon("icons/red.png"));
        jtpane2.setEditable(false);
        StyledDocument doc2 = (StyledDocument) jtpane2.getDocument();
        jtpane2.setOpaque(false);
        Style style2 = doc2.addStyle("StyleName", null);
        StyleConstants.setIcon(style2, new ImageIcon("icons/Webp.net-resizeimage.png"));

        try {
            doc2.insertString(doc2.getLength(), "No esborrar", style2);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        try {
            doc.insertString(doc.getLength(), "No esborrar", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        jlabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        this.add(jpBarres, BorderLayout.CENTER);

        JPanel jp = new JPanel(new GridLayout(2,1));
        jp.add(jtpane);
        jp.add(jtpane2);
        this.add(jp, BorderLayout.SOUTH);
        this.add(jlabel, BorderLayout.NORTH);
    }

    /**
     * Mètode que actualitza el text de la part inferior de la pantalla (acceptats / visualitzats)
     */
    public void setGraphText(int viewed, int accepted) {
        try {
            jtpane.getDocument().insertString(0,"                                                                  "+viewed+ " user profiles have been viewed   - ", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        try {
            float a = (float) viewed;
            float b = (float) accepted;
            float f = (b/a)*100;
            jtpane2.getDocument().insertString(0,"                                                                  "+accepted+ " users have been accepted ("+f+" %) - ", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que pinta les barres amb les seves altures corresponents
     * @param viewed
     * @param accepted
     */
    public void redrawGraphs(int viewed, int accepted) {
        barChart.paintBars(viewed, accepted);
        jpBarres.add(barChart);
        this.setGraphText(viewed, accepted);
    }

}

