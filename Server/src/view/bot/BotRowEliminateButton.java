package view.bot;

import javax.swing.*;

public class BotRowEliminateButton extends JButton {
    private int posicio;

    public BotRowEliminateButton(int posicio, String title){
        super(title);
        this.posicio=posicio;
    }

    public int getPosicio() {
        return posicio;
    }

    public void setPosicio(int posicio) {
        this.posicio = posicio;
    }
}
