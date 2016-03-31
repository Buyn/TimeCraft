package com.Freeman.buttons;

import com.Freeman.Alarm;
import com.Freeman.Label;
import com.Freeman.Watch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by BuYn on 07.12.2014.
 */
public abstract class Button {
    //Buttons Block
    protected JButton jButton;
    //Labels Block
    protected Alarm alAlarm;
    protected Watch wtWatch;
    //Metods Block
    public void setTextToButton(String sText){
        jButton.setText(sText);
    }
    //setter block
    //Geters block
    public JButton getjButton() {
        return jButton;
    }
}
