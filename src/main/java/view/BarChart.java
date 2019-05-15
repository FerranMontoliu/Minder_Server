package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe que implementa la creacio de les barres grafiques de la 1a pagina (Viewed / Accepted).
 */
public class BarChart extends JPanel {

    private Map<Color, Integer> bars = new LinkedHashMap<>();

    /**
     * Metode que afegeix una barra a la grafica.
     *
     * @param color Color de la barra.
     * @param value Valor de la barra.
     */
    public void addBar(Color color, int value) {
        bars.put(color, value);
    }

    /**
     * Metode que pinta un component a la grafica.
     *
     * @param g Grafica a la que s'ha de pintar el component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        int max = Integer.MIN_VALUE;
        for(Integer value: bars.values()) {
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
     * Funcio que retorna la mida desitjada de la barra.
     *
     * @return Dimensio de la barra.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bars.size() * 100 + 2, 500);
    }

    /**
     * Metode que pinta les barres de la mida desitjada.
     *
     * @param viewed Barra d'usuaris visualitzats.
     * @param accepted Barra d'usuaris acceptats.
     */
    public void paintBars(int viewed, int accepted) {
        bars.clear();
        bars.put(Color.red, viewed);
        bars.put(Color.green, accepted);
    }
}

