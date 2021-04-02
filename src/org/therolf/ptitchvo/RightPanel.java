package org.therolf.ptitchvo;

import org.therolf.ptitchvo.dice.RollListener;
import org.therolf.ptitchvo.game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.therolf.ptitchvo.GameConstants.darkColors;


public class RightPanel extends JPanel {

    private JButton playButton;
    private DicePanel dice;
    private JLabel info;

    public RightPanel() {
        this.setLayout(new GridLayout(3, 1));
        this.setMinimumSize(new Dimension(150, 0));
        this.setPreferredSize(new Dimension(150, 0));

        playButton = new JButton("Lancer le dé");
        this.add(playButton);

        dice = new DicePanel();
        this.add(dice);

        info = new JLabel("<html><b>Informations de la partie:</b><br></html>");
        info.setHorizontalAlignment(SwingConstants.LEFT);
        info.setVerticalAlignment(SwingConstants.CENTER);
        this.add(info);

        dice.addRollEndListener(v -> {
            playButton.setEnabled(true);
        });

        playButton.addActionListener(e -> {
            dice.startRoll();
            playButton.setEnabled(false);
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
        playButton.doClick();
    }
}
