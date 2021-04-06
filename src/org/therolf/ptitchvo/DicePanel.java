package org.therolf.ptitchvo;

import org.therolf.ptitchvo.dice.DiceDrawer;
import org.therolf.ptitchvo.dice.RollListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DicePanel extends JLabel {

    private static final int TURNS = 12;
    private static final int DELAY = 55;

    private Timer timer;
    private int diceTurn = 0;
    private final Random random;
    private final Random diceRandom;

    private int lastDiceValue = 0;

    private final ArrayList<RollListener> rollEnd = new ArrayList<>();

    public void addRollEndListener(RollListener rollEnd) {
        this.rollEnd.add(rollEnd);
    }

    private static DicePanel instance; //= null
    public static DicePanel getInstance() {
        if(instance == null) {
            instance = new DicePanel();
        }

        return instance;
    }

    public static int getLastDice() {
        return getInstance().lastDiceValue;
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

    private DicePanel() {
        this.setText("");
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
