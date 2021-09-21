package view.topten;

import model.Company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.PLAIN;
import static view.Typo.*;

public class BarPanel extends JPanel {
    private static final int TOP_BUFFER = 10; // where additional text is drawn
    private static final int AXIS_OFFSET = 40;

    private ArrayList<Company> companies;

    private float max, min;

    private int chartwidth, chartheight, chartX, chartY;

    private String[] xLabels, yLabels, axisLabels;

    public BarPanel(ArrayList<Company> companies){
        super();
        xLabels = new String[10];
        yLabels = new String[10];
        axisLabels = new String[8];
        getChartInfo(companies);

    }

    public void getChartInfo(ArrayList<Company> companies) {
        this.companies = companies;

        max = (float) Math.ceil(companies.get(companies.size() - 1).getShareValue());
        min = (float) Math.floor(companies.get(0).getShareValue());
        float dif = (max - min)/8;

        for(int i = 0; i < this.companies.size(); i++){
            xLabels[i] = companies.get(i).getName();
            yLabels[i] = df_cashEuro.format(companies.get(i).getShareValue());
        }

        axisLabels[0] = df_cashEuro.format(min);
        axisLabels[7] = df_cashEuro.format(max);

        for (int i = 1; i < 7; i++)
            axisLabels[i] = df_cashEuro.format(i*dif);
    }


    public void updateChart(ArrayList<Company> companies) {
        getChartInfo(companies);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        int width = this.getWidth();
        int height = this.getHeight();

        // chart area size
        chartwidth = width - 2*AXIS_OFFSET;
        chartheight = height- 2*AXIS_OFFSET - TOP_BUFFER;

        // Chart origin coords
        chartX = AXIS_OFFSET;
        chartY = height - AXIS_OFFSET;
        //int width_bar = Math.round(width*());

        //drawBars
        int numBars = companies.size();
        int barWidth = chartwidth/numBars;

        float shareValue;
        int xLeft, yTopLeft;
        int counter = 0;
        double heightaux;
        for (Company c: companies) {
            shareValue = c.getShareValue();

            heightaux = Math.ceil((shareValue/max)*chartheight);

            xLeft = AXIS_OFFSET + counter * barWidth;
            yTopLeft = chartY - (int)heightaux;

            g2D.fillRect(xLeft + 20 , yTopLeft, barWidth -20, chartY - yTopLeft);

            g2D.setFont(font_axis);
            g2D.drawString(xLabels[counter], xLeft + chartX/2, chartY + 20);
            g2D.drawString(yLabels[counter], xLeft + chartX, yTopLeft - 20);

            counter++;
        }

        //draw Axes

        int rightX = chartX + chartwidth;
        int topY = chartY - chartheight;

        g2D.drawLine(chartX, chartY, rightX, chartY);

        g2D.drawLine(chartX, chartY, chartX, topY);


        //draw Y levels
        float[] dashingPattern = {2f, 2f, 2f, 2f};
        Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1.0f, dashingPattern, 0.0f);
        g2D.setStroke(stroke);
        Font newFont = g2D.getFont().deriveFont(g.getFont().getSize() * 0.7f);
        g2D.setFont(newFont);
        for (int i = 0; i < 8; i++){
            g2D.drawString(axisLabels[i], chartX - AXIS_OFFSET, (chartY - i*chartheight/7));
            g2D.drawLine(chartX, (chartY - i*chartheight/7), chartwidth, (chartY - i*chartheight/7));
        }

    }
}
