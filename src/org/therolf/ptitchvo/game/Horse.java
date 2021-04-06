package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.DicePanel;

public class Horse {

    private static final int MAX_STAIRS = 6;
    private static final int MAX_LENGTH = 55;

    private boolean inStable = true;
    private int cellOffset = 0;
    private int length = 0;
    private int stairs = 1;

    public void move() {
        int v = DicePanel.getLastDice();
        if(v + length > MAX_LENGTH) {
            int diff = MAX_LENGTH - length;
            int left = v - diff;
            length += diff - left;
        } else {
            length += v;
        }
    }

    public boolean isInStairs() {
        return length == MAX_LENGTH;
    }

    public boolean canStairs() {
        int diceValue = DicePanel.getLastDice();
        if(!isInStairs())
            return false;

        return diceValue == stairs;
    }

    public void stairs() {
        if(!canStairs())
            return;

        ++stairs;

    }

    public boolean isInStable() {
        return inStable;
    }

    public void setInStable(boolean inStable) {
        this.inStable = inStable;

        length = 0;
        stairs = 1;
    }

    @Override
    public String toString() {
        return "{ inStable: " + inStable + ", length: " + length + ", stairs: " + stairs + "}";
    }
}
