package org.therolf.ptitchvo;

import org.therolf.ptitchvo.game.GameManager;

import javax.swing.*;
import java.awt.*;


public class RightPanel extends JPanel {

    private final JButton diceButton;

    public RightPanel() {
        this.setLayout(new GridLayout(3, 1));
        this.setMinimumSize(new Dimension(150, 0));
        this.setPreferredSize(new Dimension(150, 0));

        diceButton = new JButton("Lancer le dé");
        GameManager.getInstance().setDiceButton(diceButton);
        this.add(diceButton);

        this.add(DicePanel.getInstance());

        JLabel info = new JLabel("<html><b>Informations de la partie:</b><br></html>");
        info.setHorizontalAlignment(SwingConstants.LEFT);
        info.setVerticalAlignment(SwingConstants.CENTER);
        this.add(info);

        diceButton.addActionListener(e -> {
            diceButton.setEnabled(false);
            DicePanel.getInstance().startRoll();
        });
    }
}
