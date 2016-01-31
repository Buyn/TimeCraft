package com.Freeman;

import com.Freeman.buttons.BttnStopwatch;

import javax.swing.*;

/**
 * Created by BuYn on 09.12.2014.
 */
public class Watch  implements Runnable {
    private Label   lbWatch;
    private Timer   tTime;
    private BttnStopwatch btStopwatch;
    private boolean bPause = false;

    @Override
    public void run() {
        while (true) {
            if (!bPause) runUpdate();
            try {
                Thread.sleep(Timer.SECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * Constructors Block
     * */
    public Watch(){
        lbWatch = new Label();
        tTime = new Timer();
        lbWatch.setTime(tTime.getFormatedTime());
        runUpdate();
        btStopwatch = new BttnStopwatch(this);
    }
 /**
  * Metods Block
  * */
    public void runUpdate(){
        lbWatch.setTime(tTime.getFormatDeltaTime());
        lbWatch.updateLabel();
    }
    public void trigerPause(){
        if (bPause) {
            unPause();
            return;
        }
        setPause();
    }
    public void setPause(){
        bPause = true;
        btStopwatch.setTextToButton("Start Stopwatch");
        tTime.setPause();
    }
    public void unPause(){
        bPause = false;
        tTime.unPause();
        btStopwatch.setTextToButton("Pause Stopwatch");
    }
    public void resetTime(){
        tTime.setToZeroStart();
        runUpdate();
    }
    /**
     * Geters and Seters block
     */
    public Label getLabel() {
        return lbWatch;
    }
    public JLabel getJLabel() {
        return lbWatch.getJLabel();
    }
    public JButton getJButton() {
        return btStopwatch.getjButton();
    }
    public BttnStopwatch getButton() {
        return btStopwatch;
    }
    public Timer getTimer() {
        return tTime;
    }

}