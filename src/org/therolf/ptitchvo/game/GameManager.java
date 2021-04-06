package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.DicePanel;
import org.therolf.ptitchvo.PtitChvoPanel;
import org.therolf.ptitchvo.RightPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.therolf.ptitchvo.GameConstants.*;

public final class GameManager {

    @SuppressWarnings("PointlessBooleanExpression")
    public static boolean AUTO_CLICK = DEBUG && false;

    private static JButton diceButton; // = null
    private static PtitChvoPanel gamePanel;
    private static final ArrayList<Player> playerList = new ArrayList<>(4);
    private static int currentPlayerIndex;

    public void setDiceButton(JButton button) {
        diceButton = button;
    }

    public static void setGamePanel(PtitChvoPanel gamePanel) {
        GameManager.gamePanel = gamePanel;
    }

    private static GameManager instance = null;

    public static GameManager getInstance() {
        if(instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private GameManager() {}

    public static void start(Component c) {
        playerList.clear();
        currentPlayerIndex = 0;

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
            playerList.add(new RealPlayer(name, darkColors[playerList.size()]));
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
            playerList.add(new AIPlayer("IA " + (playerList.size() + 1), darkColors[playerList.size()]));
        }

        newRound(currentPlayerIndex);

        DicePanel.getInstance().addRollEndListener(v -> {
            Player currentPlayer = getCurrentPlayer();
            ActionPossible[] actionPossibles = currentPlayer.actionsPossible(v);

            if(actionPossibles.length > 0) {
                // autoplay if real player has one action or is ai
                if(actionPossibles.length == 1 || currentPlayer instanceof AIPlayer) {
                    currentPlayer.act(actionPossibles[0]);
                }
            } else {
                RightPanel.addLog(new ActionLog(currentPlayer.getColor(), currentPlayer.getName(), "Ne peut pas jouer"));
                actionEnded(currentPlayer);
            }
        });
    }

    /**
     * Action end listener
     * @param p the player which trigerred the listener
     */
    public static void actionEnded(Player p) {
        // repaint anyway
        gamePanel.repaint();

        // trigger actions if current player
        if(p == getCurrentPlayer()) {
            if(DicePanel.getLastDice() == 6) {
                // replay
                diceButton.setEnabled(true);

                if(GameManager.getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) {
                    diceButton.doClick();
                }
            } else {
                nextPlayer();
            }
        }
    }

    public static void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();

        newRound(currentPlayerIndex);
    }

    private static void newRound(int playerIndex) {
//        DicePanel.getInstance().hideDice();
        DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
        diceButton.setEnabled(true);

        if(getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) {
             diceButton.doClick();
        }
    }

    private static Player getCurrentPlayer() {
        return playerList.get(currentPlayerIndex);
    }

    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
