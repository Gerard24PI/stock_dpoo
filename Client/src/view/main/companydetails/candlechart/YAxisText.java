package view.main.companydetails.candlechart;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

import static view.Typo.df_axis;
import static view.Typo.font_axis;

public class YAxisText extends JPanel {
    private final int WIDTH_UNIT = 8;
    private float num;

    public YAxisText(float num) {
        super();
        this.num = num;
        int units = getUnits(this.num);
        setPreferredSize(new Dimension(15 + WIDTH_UNIT*units, 20));
    }

    /**
     * Refresh number of y axis
     * @param num updated number
     */
    public void updateNum(float num) {
        this.num = num;
        repaint();
    }

    /**
     * Returns digits of number
     * @param num number to draw
     * @return digits of that number
     */
    private int getUnits(float num) {
        if (num < 10)   return 1;
        else return 1 + getUnits(num/10);
    }

    @Override
    public void paint (Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2D.setFont(font_axis);

        float width = getWidth();
        float height = getHeight();
        g.drawString(df_axis.format(num), (int)Math.floor(width*0.05), (int)Math.floor(height*0.95));
    }
}
