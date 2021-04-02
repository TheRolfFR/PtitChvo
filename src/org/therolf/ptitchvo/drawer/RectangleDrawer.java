package org.therolf.ptitchvo.drawer;

import java.awt.*;

public class RectangleDrawer extends CenterDrawer {
    public RectangleDrawer(Directioner directioner) {
        super(directioner);
    }

    @Override
    public void draw(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int x = getX();
        int y = getY();

        g.drawRect(x - width/2, y - height/2, width, height);
    }

    @Override
    public void fill(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int x = getX();
        int y = getY();

        g.fillRect(x - width/2, y - height/2, width, height);
    }
}
