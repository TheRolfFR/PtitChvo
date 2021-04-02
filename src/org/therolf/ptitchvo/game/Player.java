package org.therolf.ptitchvo.game;

import static org.therolf.ptitchvo.GameConstants.*;

public abstract class Player {

    private int nb_chevaux_ecurie = NB_HORSES;
    private int nb_chevaux_sortis = 0;

    private String name = "";
    private Horse[] horses = {new Horse(), new Horse(), new Horse(), new Horse() };

    public Horse[] getHorses() {
        return horses;
    }

    public Player(String name) {
        this.name = name;
    }

    public abstract void play();

    @Override
    public String toString() {
        return "Player :'" + name + "' : " + nb_chevaux_ecurie +" chv en ecurie, " + nb_chevaux_sortis + " chv sortis";
    }

    public String getName() {
        return name;
    }
}
