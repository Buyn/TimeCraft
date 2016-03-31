package com.Freeman;

import com.Freeman.buttons.*;
import com.Freeman.buttons.Button;
import com.Freeman.switches.SwitchPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Created by BuYn on 03.12.2014.
 */
public class WinMain extends JFrame{
    private BttnResetAll    bResetAll;
    private ButtonExtender  bExtender;
    private ButtonExtender  bExtender10;
    private Alarm           alAlarm;
    private Watch           waWatch;
    private SwitchPanel     panelSwitchs;
    private JPanel          jpanelHidenExtender;

    public WinMain() {
    super("Alarm Stopwatch");
        setMainFrame();
    }
    private void setMainFrame(){
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setComponennts();
    }
    private void setComponennts(){
        alAlarm         = new Alarm();
        waWatch         = new Watch();
        setLabels();
        setRadioButtons();
        setButtons();
    }
    private void setLabels(){
        JPanel jpLabels = new JPanel();
        jpLabels.setLayout(new BoxLayout(jpLabels , BoxLayout.Y_AXIS));
        jpLabels.add(waWatch.getJLabel());
        jpLabels.add(alAlarm.getjProgres());
        jpLabels.add(alAlarm.getJLabel());
        add(jpLabels, BorderLayout.CENTER);
    }
    private void setButtons(){
        //set reset buttons
        bResetAll           = new BttnResetAll(alAlarm , waWatch);
        add(bResetAll.getjButton(), BorderLayout.NORTH);
        //add to panel
        JPanel jpButtons    = new JPanel();
        jpanelHidenExtender = new JPanel();
        jpanelHidenExtender.setLayout(new BoxLayout(jpanelHidenExtender, BoxLayout.Y_AXIS));
        jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.X_AXIS));
        jpButtons.add(Box.createRigidArea(new Dimension(15, 5)));
        jpButtons.add(waWatch.getJButton());
        waWatch.getJButton().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        alAlarm.getJButton().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        jpButtons.add(Box.createRigidArea(new Dimension(5, 5)));
        jpButtons.add(alAlarm.getJButton());
        jpButtons.add(Box.createRigidArea(new Dimension(5, 5)));
        bExtender = new ButtonExtender(panelSwitchs , this);
        bExtender10 = new ButtonExtender(panelSwitchs , this, "<<", ">>");
        bExtender.getjButton().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        bExtender10.getjButton().setAlignmentY(Component.BOTTOM_ALIGNMENT);//
        jpButtons.add(bExtender.getjButton());
        jpanelHidenExtender.add( new JLabel(
                //"<html><span style='font-size:12px'>+-10<br>Step</span></html>"));
                "<html>+-10<br>Minuts<br>Step</html>"));
        jpanelHidenExtender.add(bExtender10.getjButton());
        panelSwitchs.addPaneltoSwitch(jpanelHidenExtender);
        add(jpButtons, BorderLayout.SOUTH);
    }
    private void setRadioButtons(){
        panelSwitchs        = new SwitchPanel(alAlarm);
        panelSwitchs.addSwitch(3);
        panelSwitchs.addSwitch(9,true);
        panelSwitchs.addSwitch(15);
        panelSwitchs.addHidenSwitch(60);
        panelSwitchs.addHidenSwitch(90);
        panelSwitchs.addHidenSwitch(120);
        add(panelSwitchs.getjPanel(), BorderLayout.EAST);
    }
    public Button getbAlarm() {
        return alAlarm.getButton();
    }
    public Alarm getAlarm() {
        return alAlarm;
    }
    public Watch getWatch() {
        return waWatch;
    }
    public Button getbResetAll() {
        return bResetAll;
    }
}
