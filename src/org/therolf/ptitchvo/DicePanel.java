package org.therolf.ptitchvo;

import org.therolf.ptitchvo.drawer.DiceDrawer;

import javax.swing.*;
import java.awt.*;

public class DicePanel extends JLabel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                DiceDrawer.draw(g, 30 + 50*i, 30 + 50 * j, j*2 + i + 1);
            }
        }
    }

    public DicePanel() {
        this.setText("<html><div style=\"padding: 5px;\">Tour du joueur rouge: </div></html>");
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }
}
