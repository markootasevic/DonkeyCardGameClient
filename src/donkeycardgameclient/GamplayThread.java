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

    public GamplayThread(Socket s, GameWindow gameWin) {
        try {
            soket = s;
            gw = gameWin;
            objectInput = new ObjectInputStream(soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = objectInput.readObject();
                if (obj instanceof LinkedList<?>) {
                    playerList = (LinkedList<Player>) obj;
                    gw.changePlayerStatus(playerList);
                }
                if (obj instanceof Card[]) {
                    Card[] myCards = (Card[]) obj;
                    gw.getMyCards(myCards);
                }
                if (obj instanceof Card) {
                    Card card = (Card) obj;
                    gw.receveCard(card);
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
