package org.therolf.ptitchvo.drawer;

import java.awt.*;

public class DiscDrawer extends CenterDrawer {
    public DiscDrawer(Directioner directioner) {
        super(directioner);
    }

    public void setDiameter(float dia) {
        setSide(dia);
    }

    public float getDiameter() {
        return this.getWidthFloat();
    }

    @Override
    public void draw(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int x = getX();
        int y = getY();

        g.drawOval(x - width/2, y - height/2, width, height);
    }

    @Override
    public void fill(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int x = getX();
        int y = getY();

        g.fillOval(x - width/2, y - height/2, width, height);
    }
}
