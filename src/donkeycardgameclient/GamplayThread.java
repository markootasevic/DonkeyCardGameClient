/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donkeycardgameclient;

import gui.GameWindow;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Card;
import server.Player;

/**
 *
 * @author Marko
 */
public class GamplayThread extends Thread {

    ObjectInputStream objectInput = null;
    Socket soket = null;
    LinkedList<Player> playerList = new LinkedList<>();
    GameWindow gw;
    boolean receve = true;

    public GamplayThread(Socket s, GameWindow gameWin) {
        try {
            soket = s;
            gw = gameWin;
            objectInput = new ObjectInputStream(soket.getInputStream());
            receve = true;
        } catch (IOException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (receve) {
                if (!receve) {
                    return;
                }
                Object obj = null;
                while (obj == null) {
                    obj = objectInput.readObject();
                }
                if (!receve) {
                    return;
                }
                if (obj instanceof String) {
                    String reconnect = (String) obj;
                    if (reconnect.contains("Do you")) {
                        String[] array = reconnect.split("as");
                        String playerName = array[1];
                        gw.reconnectToExistingGame(reconnect, playerName);
                    }
                }
                if (obj instanceof LinkedList<?>) {
                    playerList = (LinkedList<Player>) obj;
                    for (int i = 0; i < playerList.size(); i++) {
                        if (playerList.get(i).isAreCardsDropped()) {
                            gw.someoneDroppedCards(playerList.get(i).getPlayerName());
                            break;
                        }
                    }
                    gw.changePlayerStatus(playerList);
                }

                if (obj instanceof Card) {
                    Card card = (Card) obj;
                    gw.receveCard(card);
                }
//                if(obj instanceof String) {
//                    String drop = (String) obj;
//                    gw.someoneDroppedCards(drop);
//                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopThread() {
        receve = false;
    }

}
