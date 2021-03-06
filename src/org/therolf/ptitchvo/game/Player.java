package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.DicePanel;
import org.therolf.ptitchvo.RightPanel;

import java.awt.*;
import java.util.ArrayList;

import static org.therolf.ptitchvo.GameConstants.NB_HORSES;
import static org.therolf.ptitchvo.game.ActionPossible.actions.*;

public abstract class Player {

    private final String name; // = ""
    private final Color color;
    private final Horse[] horses = {new Horse(), new Horse(), new Horse(), new Horse() };

    @SuppressWarnings("unused")
    public Horse[] getHorses() {
        return horses;
    }

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
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
        String content = "cheval " + (action.pawnIndex+1) + ": ";
        switch (action.action) {
            case STAIRS_UP:
                h.stairs();
                content += "escalier " + h.getStairs();
                break;
            case STABLE_OUT:
                h.setInStable(false);
                content += "sortie ??table";
                break;
            case MOVE:
                content += "avance de " + DicePanel.getLastDice();
                h.move();
            default:
                break;
        }

        RightPanel.addLog(new ActionLog(color, name, content));

        GameManager.actionEnded(this);
    }

    public Color getColor() {
        return color;
    }
}
