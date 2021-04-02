package org.therolf.ptitchvo.game;

public class Horse {

    private static final int MAX_STAIRS = 6;
    private static final int MAX_LENGTH = 55;

    private int cellOffset = 0;
    private int length = 0;
    private int stairs = 1;

    public Horse() {
    }

    public void move(int v) {
        if(v + length > 5) {}
    }

    public boolean canStairs() {
        return length == 5;
    }

    public boolean stairs(int v) {
        if(!canStairs())
            return false;

        if(v == stairs)
            ++stairs;

        return stairs == MAX_STAIRS;
    }
}
