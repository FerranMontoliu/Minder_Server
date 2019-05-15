package view;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Classe que implementa la grafica de les estadístiques diaries-setmanals-mensuals.
 */
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

    /**
     * Constructor del Chart.
     *
     * @param dia array de matches de cada dia o hora.
     */
    public Chart(int[] dia){
        this.data = dia;
        startX = 50;
        startY = 50;
        endX = 400;
        endY = 400;
        unitX = (350)/(dia.length -1);
        unitY = (350) / maxValue(dia);
        prevX = 50;
        prevY = 400;
    }

    /**
     * Funcio que retorna el valor maxim de les dades del dia, mes o setmana.
     *
     * @param dia array de matches de cada dia o hora.
     *
     * @return retorna el valor maxim de l'array.
     */
    private int maxValue(int[] dia) {
        int[] aux = dia;
        Arrays.sort(aux);
        return aux[aux.length - 1];
    }

    /**
     * Funcio que retorna el valor minim de les dades del dia, mes o setmana.
     *
     * @param dia array de matches de cada dia o hora.
     *
     * @return retorna el valor minim de l'array.
     */
    private int minValue(int[] dia) {
        int[] aux = dia;
        Arrays.sort(aux);
        return aux[0];
    }

    /**
     * Funcio que pinta un component de la grafica.
     *
     * @param g Grafica a la que el pinta.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Definir les x i i y inicials, finals i els increments
        startX = 50;
        startY = 50;
        endX = 400;
        endY = 400;
        unitX = 350/(data.length - 1);
        if(maxValue(data) == 0) {
            unitY = 350;
        }else{
            unitY = 350 / maxValue(data);
        }
        prevX = 50;
        prevY = (400 - (data[0] * unitY));

        //Loops que pinten la graella
        g2d.setColor(new Color(123, 140, 208));

        for (int i = startX; i <= endX; i += unitX) {
            g2d.drawLine(i, startY, i, endY);
        }

        for (int i = endY; i >= startY; i -= unitY) {
            g2d.drawLine(startX, i, endX, i);
        }

        //Linies i numeros de la taula de color negre
        g2d.setColor(Color.BLACK);
        g2d.drawString( Integer.toString(maxValue(data)),startX - 20, startY + 20);
        g2d.drawString("Matches",startX - 50, startX - 4);
        g2d.drawString( Integer.toString(0) ,startX - 20, endY);
        g2d.drawLine(startX , startY, startX, endY);
        g2d.drawLine(startX, endY, endX, endY);
        g2d.drawLine(endX, startY, endX, endY);
        g2d.drawLine(startX, startY, endX, startY);

        if(data.length == 7) {
            g2d.drawString((data.length) + " days ", endX, endY + 20);
        }
        if(data.length == 24) {
            g2d.drawString((data.length) + " hours ", endX, endY + 20);
        }
        if(data.length > 24) {
            g2d.drawString((data.length) + " days ", endX, endY + 20);

        }
        g2d.drawString( Integer.toString(1) ,startX, endY+20);

        //Línies de les coordenades de color vermell
        g2d.setColor(Color.RED);
        for(int y = 1; y < data.length; y++) {
            g2d.drawLine(prevX, prevY, prevX += unitX, prevY = (endY - (data[y] * unitY)));
        }
    }

    /**
     * Funcio que defineix el tamany del panell
     *
     * @return Dimensions del panell
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(endX + 50, endY + 50);
    }


    /**
     * Funcio que actualitza les dades de la grafica.
     *
     * @param day Array amb la nova informacio
     */
    public void updateData(int[] day){
        data = day;
    }
}
