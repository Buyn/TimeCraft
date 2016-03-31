/**
 * 
 */
package freeman.buyn.timecraft.util;

import static freeman.buyn.timecraft.util.DebugMsg.debugInfo;
import static freeman.buyn.timecraft.util.DebugMsg.debugLog;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Loading audio file in memory and waiting for command to play it
 * or command for self cleaning and ending
 * if interrupted when playing sound, is Paulese the playing 
 * @author BuYn
 *
 */
public class PlaySound implements Runnable {
	final private AtomicBoolean playSound = new AtomicBoolean(false);
	private boolean cancellad = false;
	Clip soundClip;
	/*
	 * Initialization Methods Block
	 */
	@Override
    public void run(){    	
        while (!cancellad) {
        	if (playSound.get()) runSound();
        	synchronized (playSound) {
        		try {
					playSound.wait();
				} catch (InterruptedException e) {
					debugLog("log Massage :" + "InterruptedException");
					if(cancellad) break;
				}
			}
		}
		debugLog("log Massage :" + " task PlaySound is End");
    }	
	/**
	 * Creating clip object to play sound file
	 * On directly from clip constructor
	 * Because is not working in some Linux OS
	 * 
	 * @param fileToPlay file To be Played in clip object 
	 */
	public PlaySound(File fileToPlay) {
		try {
			debugLog(fileToPlay.getAbsolutePath() + " ");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileToPlay);
			DataLine.Info lineFormat = new DataLine.Info(Clip.class, audioStream.getFormat());
			soundClip = (Clip) AudioSystem.getLine(lineFormat);
			soundClip.open(audioStream);
		}catch (LineUnavailableException e){			
			debugInfo("Info Massage :" + "LineUnavailableException in  PlaySound");
			e.printStackTrace();
		}catch (UnsupportedAudioFileException e){
			debugInfo("Info Massage :" + "UnsupportedAudioFileException in PlaySound");
			e.printStackTrace();
		}catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	/*
	 * Private Methods Block
	 */
		/*
	 * Public Methods Block
	 */
	/**
	 * Call from run circle, and start play from began
	 * 
	 */
	public void runSound(){
    	soundClip.setMicrosecondPosition(0);
    	soundClip.start();
    	try {
    		Thread.sleep(soundClip.getMicrosecondLength()/freeman.buyn.timecraft.model.clocks.Timer.SECONDS +100);    		
    		//just in case if clip not ended
    		while(soundClip.isRunning()){
    			debugInfo("soundClip.isRunning()");
    			debugLog("sleep for " + 700);
    			Thread.sleep(700);
    		} 
    	} catch (InterruptedException e) {
    		debugLog("log Massage :" + "soundClip sleep is interrupted ");
    	}      	
        soundClip.stop();
        playSound.set(false);
    }
	public void startAutio() {
		playSound.set(true);
		synchronized (playSound) {
			playSound.notifyAll();
		}
	}
	/**
	 * Stop main cikle end close open clip
	 * 
	 */
	public void end() {
		cancellad = true;
		synchronized (playSound) {
			soundClip.close();
			playSound.notifyAll();
		}		
		debugLog("soundClip close");
	}
	/*
	 * Setter/getter block
	 */
	public boolean isPlaying() {
	return soundClip.isRunning();
	}
}
