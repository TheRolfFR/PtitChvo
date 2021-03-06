package org.therolf.ptitchvo;

import org.therolf.ptitchvo.drawer.*;
import org.therolf.ptitchvo.game.GameManager;
import org.therolf.ptitchvo.game.Horse;
import org.therolf.ptitchvo.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static org.therolf.ptitchvo.GameConstants.*;

public class PtitChvoPanel extends JPanel {

    private final DiscDrawer discDrawer = new DiscDrawer();
    private final RectangleDrawer rectangleDrawer = new RectangleDrawer();
    private final PawnDrawer pawnDrawer = new PawnDrawer();
    private final Directioner textDirectioner = new Directioner(COUNT/2.f, COUNT/2.f);

    public PtitChvoPanel(int size) {
        this.resize(size);

        GameManager.setGamePanel(this);
        rectangleDrawer.setSide(ECURIE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.WHITE);

        g.setColor(Color.BLACK);

        // grille
        int height = this.getHeight();
        int width = this.getWidth();
        int spacew = width / COUNT;
        @SuppressWarnings("unused")
        int spaceh = height / COUNT;

        // directioner
        float centerx = COUNT / 2.f;
        float centery = COUNT / 2.f;
        Directioner.Direction dir;
        Directioner moving = new Directioner(centerx, centery);
        moving.setScale(spacew);

        // drawers
        discDrawer.setDirectioner(moving);
        discDrawer.setScale(spacew);
        rectangleDrawer.setDirectioner(moving);
        rectangleDrawer.setScale(spacew);
        pawnDrawer.setDirectioner(moving);
        pawnDrawer.setScale(spacew * .7f);

        // text
        textDirectioner.setScale(spacew);

        for(int colorIndex = 0; colorIndex < colors.length; ++colorIndex) {
            dir = Directioner.Direction.values()[(Directioner.Direction.values().length + colorIndex - 1) % Directioner.Direction.values().length];
            moving.setDir(dir);

            Color color = colors[colorIndex];
            g.setColor(color);

            // center triangle
            Polygon p = new Polygon();
            moving.resetMove();
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            moving.up(0.5f);
            moving.left(0.5f);
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            moving.right(1f);
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            g.fillPolygon(p);

            // ecuries
            g.setColor(darkColors[colorIndex]);
            moving.resetMove();
            rectangleDrawer.setSide(ECURIE);
            moving.move((.5f + 1 + ECURIE/2.f), -(.5f + 1 + ECURIE/2.f));
            rectangleDrawer.fill(g);
            rectangleDrawer.setSide(ECURIE * 0.90f);
            g.setColor(colors[colorIndex]);
            rectangleDrawer.fill(g);

            // carr??s centraux
            moving.resetMove();
            rectangleDrawer.setSide(CELL_SIZE);
            for(int ri = 0; ri < ECURIE; ++ri) {
                moving.up(1);
                g.setColor(colors[colorIndex]);
                rectangleDrawer.fill(g);
            }

            // cercles
            g.setColor(darkColors[colorIndex]);
            discDrawer.setDiameter(CELL_SIZE);

            moving.up(1);
            discDrawer.fill(g);
            moving.right(1);
            discDrawer.fill(g);

            // enter circle
            discDrawer.setDiameter(CELL_SIZE * 0.3f);
            g.setColor(Color.white);
            discDrawer.fill(g);
            discDrawer.setDiameter(CELL_SIZE);
            g.setColor(darkColors[colorIndex]);

            for(int di = 0; di < ECURIE; ++di) {
                moving.down(1);
                discDrawer.fill(g);
            }
            for(int di = 0; di < ECURIE; ++di) {
                moving.right(1);
                discDrawer.fill(g);
            }
        }

        // text squares central
        TextDrawer textDrawer = new TextDrawer(textDirectioner, "", g);
        textDrawer.setSize(spacew, spacew);
        textDrawer.setFont("Arial Black", Font.PLAIN, 0.8f*spacew);

        for(int i = 0; i < colors.length; ++i) {
            textDirectioner.resetMove();
            textDirectioner.down(1);

            for(int ni = 6; ni > 0; --ni) {
                g.setColor(TEXT_COLOR);
                textDrawer.draw(g, "" + ni);
                textDirectioner.down(1);
            }

            Graphics2D g2d = (Graphics2D) g;

            // rotates the coordinate by 90 degree counterclockwise
            AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI / 2.0, spacew * COUNT / 2.f, spacew * COUNT / 2.f);
            g2d.transform(rotate);
        }

        // pions
        ArrayList<Player> players = GameManager.getPlayerList();
        for (int directionIndex = 0; directionIndex < Directioner.Direction.values().length; ++directionIndex) {
            moving.setDir(Directioner.Direction.values()[directionIndex]);

            g.setColor(colors[directionIndex]);

            int horseIndex = 0;
            for(Horse h : players.get(directionIndex).getHorses()) {
                moving.resetMove();

                if(h.isInStairs()) {
                    moving.up(MAX_STAIRS - h.getStairs());
                } else if(h.isInStable()) {
                    moving.setMove(-(.5f + 1 + ECURIE/2.f), -(.5f + 1 + ECURIE/2.f));

                    // even horse left
                    if(horseIndex % 2 == 0) {
                        moving.left(1);
                    } else {
                        moving.right(1);
                    }

                    if(horseIndex < 2) {
                        moving.up(1);
                    } else {
                        moving.down(1);
                    }
                } else {
                    moving.up(MAX_STAIRS + 1);
                    moving.right(1);

                    int len = h.getLength();
                    int i = 0;
                    while (len/QUARTER > 0) {
                        moving.setDir(Directioner.Direction.values()[(++i)%Directioner.Direction.values().length]);
                        len /= QUARTER;
                    }

                    // going down
                    int tmp = ECURIE;
                    while(len > 0 && tmp > 0) {
                        moving.down(1);
                        --tmp;
                        --len;
                    }

                    if(len > 0)
                        tmp = ECURIE;
                    while (len > 0 && tmp > 0) {
                        moving.right(1);
                        --tmp;
                        --len;
                    }

                    moving.down(len);
                }

                pawnDrawer.draw(g);
                ++horseIndex;
            }
        }
    }

    public void resize(int size) {
        Dimension s = new Dimension(size, size);

        this.setPreferredSize(s);
        this.setMinimumSize(s);
    }
}
