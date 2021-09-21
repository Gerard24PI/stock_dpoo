package view.main.companydetails.candlechart;

import model.Candle;

import javax.swing.*;
import java.awt.*;

public class CandlePanel extends JPanel {
    private final float CANDLE_WIDTH_PERCENT = 55f;
    private float max, min;
    private float width, height, lsb;
    private float[] y_axis;
    private Candle candle;


    //http://edu4java.com/es/game/game1.html
    //https://jairogarciarincon.com/clase/interfaces-de-usuario-con-java-swing/la-clase-graphics
    //https://www.codejava.net/java-se/graphics/drawing-rectangles-examples-with-graphics2d
    public CandlePanel(Candle candle, float max, float min, float[] y_axis) {
        super();
        this.candle = candle;
        this.max = max;
        this.min = min;
        this.y_axis = y_axis;
    }

    /**
     * Refresh candle panel with updated information of candle
     * @param candle updated information of candle
     * @param max maximum value of chart (scale)
     * @param min minimum value of chart (scale)
     * @param y_axis y axis values for horizontal lines
     */
    public void updateCandlePanel(Candle candle, float max, float min, float[] y_axis) {
        this.candle = candle;
        this.max = max;
        this.min = min;
        this.y_axis = y_axis;
        repaint();
    }

    /**
     * Converts a numeric value to pixel
     * @param y_eur numeric value
     * @return pixel
     */
    private float getPXof(float y_eur) {
        return height - ((y_eur - min) * lsb);
    }

    @Override
    public void paint (Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //Collects information
        height = getHeight();
        width = getWidth();
        int width_candle = Math.round(width*(CANDLE_WIDTH_PERCENT/100));
        lsb = height/(max - min);                                               //px/€
        float openPrice = candle.getOpenPrice();
        float closePrice = candle.getClosePrice();

        //Set proprties of horizontal lines
        float[] dashingPattern = {10f, 10f, 10f, 10f};
        Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1.0f, dashingPattern, 0.0f);
        g2D.setStroke(stroke);
        //Draw all horizontal lines
        for (float y_axi : y_axis) {
            int y_dashed = Math.round(getPXof(y_axi));
            g2D.drawLine(0, y_dashed, Math.round(width), y_dashed);
        }
        //If there is relevant information to draw -> Draw candle
        if (candle.getMaxPrice() != -1) {
            //Draw max/min line of candle
            ////Collecting information
            int y_max = Math.round(getPXof(candle.getMaxPrice()));
            int y_min = Math.round(getPXof(candle.getMinPrice()));
            int x_center = Math.round(width / 2);
            ////Seting properties
            g2D.setColor(Color.BLACK);
            g2D.setStroke(new BasicStroke(3f));
            ////Drawing
            g2D.drawLine(x_center, y_max, x_center, y_min);

            //Draw open/close price rectangle
            ////Collecting information
            int x_rect = Math.round((width - width_candle) / 2);
            int y_rect = Math.round(getPXof(Math.max(openPrice, closePrice)));
            int height_rect = Math.round(lsb * (Math.max(openPrice, closePrice) - Math.min(openPrice, closePrice)));
            ////Seting properties
            if (openPrice >= closePrice)    g2D.setColor(Color.RED);
            else                            g2D.setColor(Color.BLUE);
            ////Drawing
            g2D.fillRect(x_rect, y_rect, width_candle, height_rect);
            g2D.setColor(Color.BLACK);
            g2D.setStroke(new BasicStroke(1.5f));
            g2D.drawRect(x_rect, y_rect, width_candle, height_rect);
        }
    }
}