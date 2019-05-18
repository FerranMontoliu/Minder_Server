package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;


/**
 * Classe que implementa la primera gràfica del servidor (Accepted/Viewed)
 */
public class JAccepted extends JPanel {
    private JTextPane jtpane;
    private JTextPane jtpane2;
    private BarChart barChart;
    private JPanel jpBarres;

    /**
     * Constructor del panell d'enregistrar
     * @param acceptats numero de perfils d'accpetats
     * @param vistos numero de perfils vistos
     */
    public JAccepted(int acceptats, int vistos) {
        this.setLayout(new BorderLayout());
        TitledBorder border = new TitledBorder("Accepted / Viewed Graph");
        border.setTitleColor(new Color(13, 71, 150));
        this.setBorder(border);
        jtpane = new JTextPane();
        jtpane2 = new JTextPane();

        jpBarres = new JPanel();
        barChart = new BarChart();

        String a = "                      ";
        JLabel jlabel = new JLabel(a + a + a + "Accepted / Viewed Users Graph Bar");
        Font f = jlabel.getFont();

        this.redrawGraphs(10,10);

        StyledDocument doc = (StyledDocument) jtpane.getDocument();
        jtpane.setOpaque(false);
        jtpane.setEditable(false);
        Style style = doc.addStyle("StyleName23", null);
        StyleConstants.setIcon(style, new ImageIcon("icons/red.png"));
        jtpane2.setEditable(false);
        StyledDocument doc2 = (StyledDocument) jtpane2.getDocument();
        jtpane2.setOpaque(false);
        Style style2 = doc2.addStyle("StyleName23", null);
        StyleConstants.setIcon(style2, new ImageIcon("icons/Webp.net-resizeimage.png"));

        try {
            doc2.insertString(doc2.getLength(), "No esborrar", style2);
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
     * Metode que actualitza el text de la part inferior de la pantalla (acceptats / visualitzats)
     *
     * @param viewed Nombre d'usuaris visualitzats.
     * @param accepted Nombre d'usuaris acceptats.
     */
    public void setGraphText(float viewed, float accepted) {
        float f = 0;
        if(viewed != 0) {
            f = (accepted/viewed) * 100;
        }
        String a = "           ";
        try {
            jtpane.setText("");
            jtpane2.setText("");
            jtpane.getDocument().insertString(0,a+a+a+a+a+a+ (int) viewed + " user profiles have been viewed ", null);
            jtpane2.getDocument().insertString(0,a+a+a+a+a+a+ (int) accepted + " users have been accepted (" + f + " %)", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode que pinta les barres amb les seves altures corresponents.
     * @param viewed Nombre de perfils visualitzats.
     * @param accepted Nombre de perfils acceptats.
     */
    public void redrawGraphs(int viewed, int accepted) {
        barChart.paintBars(viewed, accepted);
        jpBarres.add(barChart);
        this.setGraphText(viewed, accepted);
    }
}

