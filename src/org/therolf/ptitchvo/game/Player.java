package org.therolf.ptitchvo.game;

import java.util.ArrayList;

import static org.therolf.ptitchvo.GameConstants.NB_HORSES;
import static org.therolf.ptitchvo.game.ActionPossible.actions.*;

public abstract class Player {

    private final String name; // = ""
    private final Horse[] horses = {new Horse(), new Horse(), new Horse(), new Horse() };

    @SuppressWarnings("unused")
    public Horse[] getHorses() {
        return horses;
    }

    public Player(String name) {
        this.name = name;
    }

    public int horsesIn() {
        int res = 0;
        for(Horse h : horses) {
            if(h.isInStable())
                ++res;
        }

        return res;
    }

    public int horsesOut() {
        return NB_HORSES - horsesIn();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Player :'" + name + "' : " + horsesIn() +" chv en ecurie, " + horsesOut() + " chv sortis");

        for(Horse h : horses) {
            builder.append("\r\n");
            builder.append(h.toString());
        }

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public ActionPossible[] actionsPossible(int diceValue) {
        ArrayList<ActionPossible> actionPossibles = new ArrayList<>(horses.length);

        for(int i = 0; i < horses.length; ++i) {
            Horse h = horses[i];
            if(h.isInStable()) {
                if(diceValue == 6) {
                    actionPossibles.add(new ActionPossible(STABLE_OUT, i));
                }
            } else {
                if(h.canStairs()) {
                    actionPossibles.add(new ActionPossible(STAIRS_UP, i));
                } else {
                    actionPossibles.add(new ActionPossible(MOVE, i));
                }
            }
        }

        actionPossibles.sort(new ActionPossible(MOVE, 0));

        return actionPossibles.toArray(new ActionPossible[0]);
    }

    public void act(ActionPossible action) {
        Horse h = horses[action.pawnIndex];
        switch (action.action) {
            case STAIRS_UP:
                h.stairs();
                break;
            case STABLE_OUT:
                h.setInStable(false);
                break;
            case MOVE:
                h.move();
            default:
                break;
        }

        GameManager.actionEnded(this);
    }
}
