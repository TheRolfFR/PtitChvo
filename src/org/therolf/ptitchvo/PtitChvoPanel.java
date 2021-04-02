package org.therolf.ptitchvo;

import org.therolf.ptitchvo.drawer.Directioner;
import org.therolf.ptitchvo.drawer.DiscDrawer;
import org.therolf.ptitchvo.drawer.RectangleDrawer;
import org.therolf.ptitchvo.drawer.TextDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

public class PtitChvoPanel extends JPanel {

    private static final int COUNT = 15;
    private static final int ECURIE = 6;
    private static final int CENTER = COUNT/2;

    private static final float CELL_SIZE= 0.85f;

    public static final Color[] colors = { new Color(0xffe783), new Color(0x95e5e5), new Color(0xff896a), new Color(0xcfff39) };
    public static final Color[] darkColors = { new Color(0xffcd00), new Color(0x2fcdcd), new Color(0xff3000), new Color(0x9acd00) };

    private static final Color textColor = new Color(0x818181);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.WHITE);

        g.setColor(Color.BLACK);

        // grille
        int height = this.getHeight();
        int width = this.getWidth();
        int spacew = width / COUNT;
        int spaceh = height / COUNT;


        for(int colorIndex = 0; colorIndex < colors.length; ++colorIndex) {
            Color color = colors[colorIndex];
            g.setColor(color);

            Polygon p = new Polygon();
            Directioner moving = new Directioner(COUNT/2.f, COUNT/2.f, Directioner.Direction.values()[(Directioner.Direction.values().length  + colorIndex - 1) % Directioner.Direction.values().length]);
            moving.setScale(spacew);

            // center triangle
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            moving.up(0.5f);
            moving.left(0.5f);
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            moving.right(1f);
            p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
            g.fillPolygon(p);

            // ecuries
            g.setColor(darkColors[colorIndex]);
            RectangleDrawer rectangleDrawer = new RectangleDrawer(moving);
            rectangleDrawer.setScale(spacew);
            rectangleDrawer.setSide(ECURIE);
            moving.setMove(0, 0);
            moving.move((.5f + 1 + ECURIE/2.f), -(.5f + 1 + ECURIE/2.f));
            rectangleDrawer.fill(g);
            rectangleDrawer.setSide(ECURIE * 0.90f);
            g.setColor(colors[colorIndex]);
            rectangleDrawer.fill(g);

            // carrés centraux
            moving.setMove(0, 0);
            rectangleDrawer.setSide(CELL_SIZE);
            for(int ri = 0; ri < ECURIE; ++ri) {
                moving.up(1);
                g.setColor(colors[colorIndex]);
                rectangleDrawer.fill(g);
            }

            // cercles
            g.setColor(darkColors[colorIndex]);
            moving.up(1);
            DiscDrawer discDrawer = new DiscDrawer(moving);
            discDrawer.setScale(spacew);
            discDrawer.setDiameter(CELL_SIZE);
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
        Directioner textDirectioner = new Directioner(COUNT/2.f, COUNT/2.f);
        textDirectioner.setScale(spacew);
        TextDrawer textDrawer = new TextDrawer(textDirectioner, "", g);
        textDrawer.setSize(spacew, spacew);
        textDrawer.setFont("Arial Black", Font.PLAIN, 0.8f*spacew);
        for(int i = 0; i < colors.length; ++i) {
            textDirectioner.setMove(0, 0);
            textDirectioner.down(1);

            for(int ni = 6; ni > 0; --ni) {
                g.setColor(textColor);
                textDrawer.draw(g, "" + ni);
                textDirectioner.down(1);
            }

            Graphics2D g2d = (Graphics2D) g;

            // rotates the coordinate by 90 degree counterclockwise
            AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI / 2.0, spacew * COUNT / 2.f, spacew * COUNT / 2.f);
            g2d.transform(rotate);
        }
    }

    public PtitChvoPanel(int size) {
        this.resize(size);
    }

    public void resize(int size) {
        Dimension s = new Dimension(size, size);

        this.setPreferredSize(s);
        this.setMinimumSize(s);
    }
}