package view;

import java.awt.*;
import java.text.DecimalFormat;

public class Typo {

    public static Font fontBoldLarge = new Font("SANS_SERIF", Font.BOLD, 30);
    public static Font fontBoldMedium = new Font("SANS_SERIF", Font.BOLD, 15);
    public static Font fontNumbers = new Font("SANS_SERIF", Font.BOLD, 20);
    public static Font font_axis = new Font("Latin Modern Math", Font.PLAIN, 13);

    public static DecimalFormat df_cashEuro = new DecimalFormat("0.00 €");
    public static DecimalFormat df_perCent = new DecimalFormat("0.00 %");
    public static DecimalFormat df_axis = new DecimalFormat("0.0");

}
