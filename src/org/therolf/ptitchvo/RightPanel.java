package org.therolf.ptitchvo;

import org.therolf.ptitchvo.dice.RollListener;
import org.therolf.ptitchvo.game.GameManager;

import javax.swing.*;
import java.awt.*;


public class RightPanel extends JPanel {

    private JButton diceButton;
    private DicePanel dice;
    private JLabel info;

    public RightPanel() {
        this.setLayout(new GridLayout(3, 1));
        this.setMinimumSize(new Dimension(150, 0));
        this.setPreferredSize(new Dimension(150, 0));

        diceButton = new JButton("Lancer le dé");
        GameManager.getInstance().setDiceButton(diceButton);
        this.add(diceButton);

        dice = new DicePanel();
        GameManager.getInstance().setDicePanel(dice);
        this.add(dice);

        info = new JLabel("<html><b>Informations de la partie:</b><br></html>");
        info.setHorizontalAlignment(SwingConstants.LEFT);
        info.setVerticalAlignment(SwingConstants.CENTER);
        this.add(info);

        dice.addRollEndListener(v -> {
            diceButton.setEnabled(true);
        });

        diceButton.addActionListener(e -> {
            dice.startRoll();
            diceButton.setEnabled(false);
        });
    }

    public void hideDice() {
        dice.hideDice();
    }

    public void setPlayer(int playerIndex, String name) {
        dice.setPlayer(playerIndex, name);
    }

    public void addRollEndListener(RollListener rl) {
        dice.addRollEndListener(rl);
    }

    public void triggerRoll() {
        diceButton.doClick();
    }
}
