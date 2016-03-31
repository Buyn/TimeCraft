package com.Freeman.buttons;

import com.Freeman.WinMain;
import com.Freeman.switches.SwitchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by BuYn on 18.01.2015.
 */
public class ButtonExtender {
    private JButton jButton;
    private boolean bOpen = false;
    private SwitchPanel panelSwitchers;
    private WinMain jFrame;
    private String sButton[];


    /**
     * Constructors Block
     * action listener using function -
     * trigerButton as main logic
     */
    public ButtonExtender(SwitchPanel swPanel, WinMain newjFrame){
        jFrame = newjFrame;
        sButton = new String[2];
        sButton[0] = ">>";
        sButton[1] = "<<";
        panelSwitchers = swPanel;
        panelSwitchers.getjPanel().setVisible(false);
        jButton =  new JButton(sButton[0]);
        jButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 trigerButton();
             }
        });
    }
    public ButtonExtender(SwitchPanel swPanel, WinMain newjFrame, String sButtonTextON, String sButtonTextOFF){
        jFrame =  newjFrame;
        sButton = new String[2];
        sButton[0] = sButtonTextOFF;
        sButton[1] = sButtonTextON;
        panelSwitchers = swPanel;
        panelSwitchers.getPanelHidenSwitchers().setVisible(false);
        jButton =  new JButton(sButton[0]);
        jButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 trigerButton(panelSwitchers.getPanelHidenSwitchers());
             }
        });
    }
    /**
    * main logic
    * on state variable bOpen decide what to execute
    * closePanel or openPanel
     * */
    private void trigerButton(JPanel jPanel){
        if (bOpen){
            closePanel(jPanel);
            return;
        }
        openPanel(jPanel);
    }
    private void trigerButton(){
        if (bOpen){
            closePanel();
            return;
        }
        openPanel();
    }
    //Metods
    private void closePanel(JPanel jPanel){
        bOpen = false;
        jPanel.setVisible(false);
        jFrame.pack();
        jButton.setText(sButton[0]);
    }
    private void closePanel(){
        bOpen = false;
        panelSwitchers.getjPanel().setVisible(false);
        jFrame.pack();
        jButton.setText(sButton[0]);
    }
    private void openPanel(){
        bOpen = true;
        panelSwitchers.getjPanel().setVisible(true);
        jFrame.pack();
        jButton.setText(sButton[1]);
    }
    private void openPanel(JPanel jPanel){
        bOpen = true;
        jPanel.setVisible(true);
        jFrame.pack();
        jButton.setText(sButton[1]);
    }
    //GETER
    public JButton getjButton() {
        return jButton;
    }
}
