package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe que implementa la creació de les barres gràfiques de la 1a pàgina (Viewed / Accepted)
 */
public class BaarChart extends JPanel {

    private Map<Color, Integer> bars = new LinkedHashMap<>();

    public void addBar(Color color, int value) {
        bars.put(color, value);
    }

    /**
     * Mètode que crea una barra gràfica
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        int max = Integer.MIN_VALUE;

        for (Integer value : bars.values()) {

            max = Math.max(max, value);
        }

        int width = (getWidth() / bars.size()) - 2;
        int x = 1;

        for (Color color : bars.keySet()) {
            int value = bars.get(color);
            int height = (int) ((getHeight() - 5) * ((double) value / max));
            g.setColor(color);
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            g.drawRect(x, getHeight() - height, width, height);
            x += (width + 2);
        }
    }

    /**
     * Mètode que retorna la mida desitjada de la barra
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bars.size() * 100 + 2, 500);
    }

    /**
     * Mètode que pinta les barres de la mida desitjada
     * @param viewed
     * @param accepted
     */
    public void paintBars(int viewed, int accepted) {
        bars.clear();
        bars.put(Color.red, viewed);
        bars.put(Color.green, accepted);
    }
}

