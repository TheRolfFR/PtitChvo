package org.therolf.ptitchvo;

import javax.swing.*;
import java.awt.*;


public class RightPanel extends JPanel {

    private JButton playButton;
    private DicePanel dice;
    private JLabel info;

    public RightPanel() {
        this.setLayout(new GridLayout(3, 1));
        this.setMinimumSize(new Dimension(150, 0));
        this.setPreferredSize(new Dimension(150, 0));


        playButton = new JButton("Jouer");
        this.add(playButton);

        dice = new DicePanel();
        this.add(dice);

        info = new JLabel("<html><b>Informations de la partie:</b><br>"
                + "<div>- <span color=\"#" + Integer.toHexString(PtitChvoPanel.darkColors[0].getRGB() & 0xFFFFFF) + "\">Jaune</span> : 0 chevaux arrivés</div>"
                + "<div>- <span color=\"#" + Integer.toHexString(PtitChvoPanel.darkColors[1].getRGB() & 0xFFFFFF) + "\">Bleu</span> : 0 chevaux arrivés</div>"
                + "<div>- <span color=\"#" + Integer.toHexString(PtitChvoPanel.darkColors[2].getRGB() & 0xFFFFFF) + "\">Rouge</span> : 0 chevaux arrivés</div>"
                + "<div>- <span color=\"#" + Integer.toHexString(PtitChvoPanel.darkColors[3].getRGB() & 0xFFFFFF) + "\">Vert</span> : 0 chevaux arrivés</div>"
                + "</html>");
        info.setHorizontalAlignment(SwingConstants.LEFT);
        info.setVerticalAlignment(SwingConstants.CENTER);
        this.add(info);
    }
}
