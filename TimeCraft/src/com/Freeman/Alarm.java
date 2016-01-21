package com.Freeman;
import com.Freeman.buttons.BttnAlarm;
import com.Freeman.buttons.Button;
import javax.swing.*;

/**
 * Created by BuYn on 09.12.2014.
 */
public class Alarm implements Runnable {
    private Label lbAlarm;
    private BttnAlarm btAlarm;
    private Timer tTime;
    private AlarmSound asSound;
    private JProgressBar jProgres;
    private boolean bStop = false;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Timer.SEKUNDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if(bStop) continue;
            runUpdate();
            if (isAlarmSetoff()){
                playSound();
                resetAlarm(1);
            }
        }
    }
    /**
     * Constructors Block
     */
    public Alarm() {
        asSound = new AlarmSound();
        lbAlarm = new Label("Alarm: ");
        tTime = new Timer();
        lbAlarm.setTime(tTime.getFormatedTime());
        jProgres = new JProgressBar( -(int)tTime.getSekundsLeft() , 0);
        jProgres.setStringPainted(true);
        jProgres.setString("");
        runUpdate();
        btAlarm = new BttnAlarm(this);

    }
    /**
     * Metods block
     */
    public void trigerStop(){
        //uncoment for testing sound on button press
        //playSound();
        if (bStop) {
            setReset();
            return;
        }
        setStop();
    }
    public void setStop(){
        bStop = true;
        btAlarm.setTextToButton("Reset Alarm");
    }
    public void setReset(){
        bStop = false;
        btAlarm.setTextToButton("Stop Alarm");
        resetAlarm();
    }
    public void runUpdate() {
        lbAlarm.setTime(tTime.getFormatTimeLeft());
        jProgres.setValue(-(int)tTime.getSekundsLeft());
        jProgres.setString(String.valueOf(tTime.getSekundsLeft()));
        lbAlarm.updateLabel();
    }
    public boolean isAlarmSetoff (){
        if (tTime.getTimeLeft() <= 500) return true;
        return false;
    }
    public void resetAlarm(int iMinuts) {
        tTime.setAlarm(iMinuts);
        lbAlarm.updateLabel();
    }
    public void resetAlarm() {
        tTime.setAlarm();
        lbAlarm.updateLabel();
    }

    public void playSound(){
        Thread thSound = new Thread(asSound);
        thSound.start();
    }
    public void setTimerAlarm(int iMinuts){
        tTime.setAlarmSet(iMinuts);
    }
    /**
     * Geters and Seters block
     */
    public Label getLabel() {
        return lbAlarm;
    }
    public JLabel getJLabel() {
        return lbAlarm.getJLabel();
    }
    public Button getButton() {
        return btAlarm;
    }
    public JButton getJButton() {
        return btAlarm.getjButton();
    }
    public Timer getTimer() {
        return tTime;
    }
    public JProgressBar getjProgres() {
        return jProgres;
    }
}