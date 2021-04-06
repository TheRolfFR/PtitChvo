package org.therolf.ptitchvo.game;

import java.util.Comparator;

public class ActionPossible implements Comparable<ActionPossible>, Comparator<ActionPossible> {
    @Override
    public int compareTo(ActionPossible o) {
        if(this.action == o.action) return 0;

        if(this.action == actions.MOVE) return 1;
        if(this.action == actions.STAIRS_UP) return -1;

        // I am stable out
        // return if other  : move=-1, stairs_up = 1
        return (o.action == actions.MOVE) ? -1 : 1;
    }

    @Override
    public int compare(ActionPossible o1, ActionPossible o2) {
        return o1.compareTo(o2);
    }

    public enum actions {
        MOVE,
        STABLE_OUT,
        STAIRS_UP
    }

    public actions action;
    public int pawnIndex;

    public ActionPossible(actions action, int pawnIndex) {
        this.action = action;
        this.pawnIndex = pawnIndex;
    }
}
