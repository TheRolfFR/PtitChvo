package org.therolf.ptitchvo.drawer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DiceDrawer {

    private static final int SIZE = 43;
    private static final int CI_SIZE = 6;
    private static final int BOR = 1;

    private static final float leftPercent = 1.f/4.f;
    private static final float rightPercent = 1f - leftPercent;

    private static final Color DICE_BG = Color.white;
    private static final Color DICE_BORDER = Color.black;
    private static final Color DICE_VAL = Color.black;

    public static void draw(Graphics g, int x, int y, int v) {
        if(v < 1 || v > 6)
            return;

        g.setColor(DICE_BG);
        g.fillRect(x, y, SIZE, SIZE);

        g.setColor(DICE_BORDER);
        g.drawRect(x, y, SIZE, SIZE);

        g.setColor(DICE_VAL);
        // nombres impairs ont le rond au centre
        if(v %2 == 1)
            drawPoint(g, x, y, .5f, .5f);

        // plus grand ou egal a 2 : deux point diagonale nord-ouest
        if(v >= 2) {
            drawPoint(g, x, y, leftPercent, leftPercent);
            drawPoint(g, x, y, rightPercent, rightPercent);
        }

        // plus grand ou egal a 4 : deux point diagonale nord-est
        if(v >= 4) {
            drawPoint(g, x, y, rightPercent, leftPercent);
            drawPoint(g, x, y, leftPercent, rightPercent);
        }

        // 6 : deux points ligne milieu
        if(v == 6) {
            drawPoint(g, x, y, leftPercent, .5f);
            drawPoint(g, x, y, rightPercent, .5f);
        }
    }

    private static void drawPoint(Graphics g, int x, int y, float percentX, float percentY) {
        // offset of border
        int offsetX = x + BOR;
        int offsetY = y + BOR;

        // remove border to working side size
        int workingSide = SIZE+1 - BOR*2;

        // offset of a certain percentage
        if(percentX > 0.5f)
            offsetX += workingSide - (1f - percentX)*workingSide;
        else
            offsetX += percentX * workingSide;

        if(percentY > 0.5f)
            offsetY += workingSide - (1f - percentY)*workingSide;
        else
            offsetY += percentY * workingSide;

        // remove halg of dice circle
        offsetX -= CI_SIZE / 2;
        offsetY -= CI_SIZE / 2;

        g.fillRect(offsetX, offsetY, CI_SIZE, CI_SIZE);
    }
}
