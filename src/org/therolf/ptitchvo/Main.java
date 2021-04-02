package org.therolf.ptitchvo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main extends JFrame {

    private static final int PLATEAU_SIDE = 800;

    PtitChvoPanel plateau = new PtitChvoPanel(PLATEAU_SIDE);

    Main() {
        this.setSize(1200, PLATEAU_SIDE);
        this.setTitle("PtitChvo");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
//        this.setResizable(false);

        this.getContentPane().add(plateau, BorderLayout.CENTER);

        RightPanel panel = new RightPanel();
        this.getContentPane().add(panel, BorderLayout.EAST);

        this.pack();
        this.setVisible(true);

        Main m = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newd = new Dimension(0, plateau.getWidth() + m.getInsets().top);
                m.setMinimumSize(newd);
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
