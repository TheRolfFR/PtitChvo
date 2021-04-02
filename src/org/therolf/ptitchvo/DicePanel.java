package org.therolf.ptitchvo;

import org.therolf.ptitchvo.dice.RollListener;
import org.therolf.ptitchvo.dice.DiceDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DicePanel extends JLabel {

    private static final int TURNS = 12;
    private static final int DELAY = 55;

    private Timer timer;
    private int diceTurn = 0;
    private Random random;
    private Random diceRandom;

    private int lastDiceValue = 0;

    private ArrayList<RollListener> rollEnd = new ArrayList<>();

    public void addRollEndListener(RollListener rollEnd) {
        this.rollEnd.add(rollEnd);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        DiceDrawer.draw(g, 30, 50, lastDiceValue);
    }

    public void startRoll() {
        timer.start();
        diceTurn = 1;
    }

    public void hideDice() {
        lastDiceValue = 0;
        this.repaint();
    }

    public DicePanel() {
        this.setText("<html><div style=\"padding: 5px;\">Tour du joueur rouge</div></html>");
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.CENTER);

        random = new Random();
        diceRandom = new Random();

        timer = new Timer(DELAY, e -> {

            ++diceTurn;

            if(diceTurn == TURNS) {
                timer.stop();
                diceTurn = 0;

                lastDiceValue = 1 + diceRandom.nextInt(6);
                this.repaint();

                for(RollListener l : rollEnd) l.onRollEnded(lastDiceValue);
            } else {
                lastDiceValue = 1 + random.nextInt(6);
                this.repaint();
            }
        });
    }

    public void setPlayer(int playerIndex, String playerName) {
        this.setText("<html>C'est le tour de <b style='color: #" + Integer.toHexString(GameConstants.darkColors[playerIndex].getRGB() & 0xFFFFFF) + "'>" + playerName + "</b></html>");
    }
}
