package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.PtitChvoPanel;
import org.therolf.ptitchvo.RightPanel;
import org.therolf.ptitchvo.dice.RollListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.therolf.ptitchvo.GameConstants.*;

public class GameManager {

    private PtitChvoPanel gamePanel = null;
    private RightPanel rightPanel = null;

    private ArrayList<Player> playerList = new ArrayList<>(4);

    private int currentPlayerIndex = 0;

    public GameManager(Component c, PtitChvoPanel gamePanel, RightPanel rightPanel) {
        this.gamePanel = gamePanel;
        this.rightPanel = rightPanel;

        int nb = -1;

        // nb players
        do {
            if(DEBUG) {
                nb = NB_REAL_PLAYERS;
            } else {
                String cnt = JOptionPane.showInputDialog(c,
                        "Combien de joueurs ?", MIN_PLAYER);
                try {
                    nb = Integer.parseInt(cnt);
                } catch (NumberFormatException ignored) {}
            }
        } while(nb < MIN_PLAYER || nb > MAX_PLAYER);

        int i = 0;
        for(; i < nb; ++i) {
            String name;
            if(DEBUG) {
                name = "plop " + (i+1);
            } else {
                name = JOptionPane.showInputDialog(c,
                        "Saisir le nom du joueur " + (i+1), "Yann");
            }
            playerList.add(new RealPlayer(name));
        }

        // nb bots
        do
        {
            if(DEBUG) {
                nb = NB_BOT_PLAYERS;
            } else {
                String cnt = JOptionPane.showInputDialog(c,
                        "Combien de bots ?", MIN_PLAYER);
                try {
                    nb = Integer.parseInt(cnt);
                } catch (NumberFormatException ignored) {}
            }
        } while (nb < 0 || nb > (MAX_PLAYER - playerList.size()));

        for(i = 0; i < nb; ++i) {
            playerList.add(new AIPlayer("IA " + (playerList.size() + 1)));
        }

        for(Player p : playerList) {
            System.out.println(p);
        }

        rightPanel.addRollEndListener(v -> {
            nextPlayer();
        });
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();

        newRound(currentPlayerIndex);
    }

    private void newRound(int playerIndex) {
        rightPanel.hideDice();
        rightPanel.setPlayer(playerIndex, playerList.get(playerIndex).getName());

        if(currentPlayer() instanceof AIPlayer) {
            rightPanel.triggerRoll();
        }
    }

    public Player currentPlayer() {
        return playerList.get(currentPlayerIndex);
    }
}
