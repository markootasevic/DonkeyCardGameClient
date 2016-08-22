/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donkeycardgameclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author Marko
 */
public class Stopwatch extends Thread {

    boolean stop = false;
    double counter;

    public double count() {
        return counter / 100;
    }

    public void stopTime() {
        stop = true;
    }

    @Override
    public void run() {
        counter = 0;
        while (stop == false) {
            try {
                sleep(10);
                counter++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Stopwatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
