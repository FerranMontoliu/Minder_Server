package view;
import javax.swing.*;
import java.awt.*;

public class Chart extends JPanel {
    private int[] data ;
    private int startX ;
    private int startY ;
    private int endX ;
    private int endY ;
    private int unitX ;
    private int unitY;
    private int prevX;
    private int prevY;

    public Chart(int[] dia, String nom){
        this.data = dia;
        startX = 50;
        startY = 50;
        endX = 400;
        endY =400;
        unitX = (endX -startX)/dia.length;
        unitY = (endY - startY) / maxValue(dia);
        prevX = startX;
        prevY = endY;



    }

    private int maxValue(int[] dia) {
        int max = dia[0];
        for (int ktr = 0; ktr < dia.length; ktr++) {
            if (dia[ktr] > max) {
                max = dia[ktr];
            }
        }
        return max;
    }

    private int minValue(int[] dia) {
        int min = dia[0];
        for (int ktr = 0; ktr < dia.length; ktr++) {
            if (dia[ktr] < min) {
                min = dia[ktr];
            }
        }
        return min;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        startX = 50;
        startY = 50;
        endX = 400;
        endY =400;
        unitX = (endX -startX)/data.length;
        unitY = (endY - startY) / maxValue(data);
        prevX = startX;
        prevY = endY;

        //We draw in the following 2 loops the grid so it's visible what I explained before about each "unit"
        g2d.setColor(new Color(123, 140, 208));

        for (int i = startX; i <= endX; i += unitX) {
            g2d.drawLine(i, startY, i, endY);
        }


        for (int i = endY; i >= startY; i -= unitY) {
            g2d.drawLine(startX, i, endX, i);
        }

        //Linies i numeros de la taula de color negre
        g2d.setColor(Color.BLACK);
        g2d.drawString( Integer.toString(maxValue(data)) ,startX-20, startY+20);
        g2d.drawString( Integer.toString(0) ,startX-20, endY);
        g2d.drawLine(startX  , startY, startX, endY);
        g2d.drawLine(startX, endY, endX, endY);
        g2d.drawLine(endX, startY, endX, endY);
        g2d.drawLine(startX, startY, endX, startY);

        g2d.drawString( Integer.toString(data.length) ,endX, endY+20);
        g2d.drawString( Integer.toString(0) ,startX, endY+20);


        //We draw each of our coords in red color
        g2d.setColor(Color.RED);
        for (int y : data) {
            g2d.drawLine(prevX, prevY, prevX += unitX, prevY = (endY - (y * unitY)));
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(endX + 50, endY + 50);
    }

    public void setData(int[] dia) {
        this.data = dia;
    }


    public void update(int[] day, String name){
        data = day;


    }
}
