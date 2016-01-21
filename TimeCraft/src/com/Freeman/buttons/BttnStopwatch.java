package com.Freeman.buttons;
import com.Freeman.Watch;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by BuYn on 07.12.2014.
 */
public class BttnStopwatch extends Button{
    public BttnStopwatch(Watch wtNewWatch){
        super.wtWatch = wtNewWatch;
        jButton = new JButton("Pause Stopwatch");
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wtWatch.trigerPause();
                wtWatch.runUpdate();
            }
        });
    }
    //Metods Block
    //Geters block
}
