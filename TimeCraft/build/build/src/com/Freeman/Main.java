package com.Freeman;

public class Main {

    public static void main(String[] args) {
//	write your code here
        WinMain winMain = new WinMain();
        winMain.setVisible(true);
        winMain.pack();
        Thread thAlarm = new Thread(winMain.getAlarm());
        Thread thWatch = new Thread(winMain.getWatch());
        thAlarm.start();
        thWatch.start();
    }
}