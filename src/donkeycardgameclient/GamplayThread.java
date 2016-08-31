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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marko
 */
public class GamplayThread extends Thread{
    ObjectInputStream objectInput = null;
    Socket soket = null;

    public GamplayThread(Socket s, GameWindow gw) {
        try {
            soket = s;
            objectInput = new ObjectInputStream(soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
    @Override
    public void run() {
        try {
            Object obj = objectInput.readObject();
            
        } catch (IOException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GamplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
