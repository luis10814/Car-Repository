/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign4;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vangelis
 */
public class BusyThread extends Thread {
    private boolean busy = true;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public void run() {
        System.out.print("\nWorking.");
        while (busy) {
            try {
                System.out.print(".");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BusyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Done!");
    }
}
