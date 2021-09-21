package view.bot;

import javax.swing.*;

public class BotRowChangeStatus extends JRadioButton {
    private int posicio;

    public BotRowChangeStatus(int posicio, String title){
        super(title);
        this.posicio=posicio;
    }

    public int getPosicio() {
        return posicio;
    }
}
