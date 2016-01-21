package com.Freeman;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

/**
 * Created by BuYn on 08.01.2015.
 */
public class AlarmSound implements Runnable {
    //String sFileUrl = "/alarm.wav";
    String sFileUrl = "alarm.wav";
    String sJarUrl = "/Res/alarm.wav";
//    String sJarUrl = "/com/Freeman/alarm.wav";
    URL     uUrl;
    boolean bJar = false;

    @Override
    public void run(){
        runSound();
    }

    public void runSound(){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(getStrim());
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private AudioInputStream getStrim(){
        if (bJar) return getStrimFromJar();
        return getStrimFromFile();
    }
    private AudioInputStream getStrimFromFile(){
        try {
        return  AudioSystem.getAudioInputStream(new File(sFileUrl));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    private AudioInputStream getStrimFromJar1(){
        try {
            uUrl = getClass().getResource(sJarUrl);
            return  AudioSystem.getAudioInputStream(uUrl);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    private AudioInputStream getStrimFromJar(){
        try {
            uUrl = getClass().getResource(sJarUrl);
        System.out.println("AlarmSound.getStrimFromJar " + uUrl);
            return  AudioSystem.getAudioInputStream(getClass().getResourceAsStream(sJarUrl));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}