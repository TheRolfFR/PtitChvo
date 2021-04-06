package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.DicePanel;

import static org.therolf.ptitchvo.GameConstants.MAX_LENGTH;

public class Horse {

    private boolean inStable = true;
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

    public int getStairs() {
        return stairs;
    }

    public int getLength() {
        return length;
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
