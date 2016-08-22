/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donkeycardgameclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.w3c.dom.ranges.Range;

/**
 *
 * @author Marko
 */
public class Timer extends Thread {

    boolean run = true;
    boolean stop = false;
    JLabel labela = null;

    public Timer(JLabel l) {
        labela = l;
    }

    public void run() {
        try {

            for (int i = 30; i >= 0; i--) {
                if (stop) {
                    return;
                }
                if (!run) {

                    synchronized (this) {
                        wait();
                    }
                }
                labela.setText("" + i);
                sleep(1000);
            }
            return;

        } catch (InterruptedException ex) {
            labela.setText("desilo se sranje");
        }
    }

    public synchronized void kill() {
        stop = true;
    }

    public synchronized void pause() {
        run = false;
    }

    public synchronized void go() {
        run = true;
        synchronized (this) {
            notifyAll();
        }
    }

    public static void main() {
        System.out.println("caooo");
    }
}
