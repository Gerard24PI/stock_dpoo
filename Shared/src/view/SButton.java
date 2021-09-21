package view;

import javax.swing.*;
import java.awt.*;

public class SButton extends JButton {

    public SButton(String msg){
        super(msg);
    }

    public SButton(String msg, String textColor, String backgroundColor){
        super(msg);
        this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));

        switch (textColor){
            case "white":
                this.setForeground(new Color(255, 255, 255));
                break;
            case "black":
                this.setForeground(new Color(0, 0, 0));
                break;
            case "green":
                this.setForeground(new Color(71, 209, 71));
                break;
            case "red":
                this.setForeground(new Color(255, 51, 51));
                break;
        }

        switch (backgroundColor){
            case "white":
                this.setBackground(new Color(255, 255, 255));
                break;
            case "black":
                this.setBackground(new Color(0, 0, 0));
                break;
            case "green":
                this.setBackground(new Color(71, 209, 71));
                break;
            case "red":
                this.setBackground(new Color(255, 51, 51));
                break;
        }

    }

}
