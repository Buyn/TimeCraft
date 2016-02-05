/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import java.io.File;
import freeman.buyn.timecraft.util.PlaySound;

/**
 * Create too threads from file path to audio file
 * it can be path from default String or parameter for constructor on creation 
 * and playing them on command
 * 
 * @author BuYn
 *
 */
public class AlarmSound {
	final private static  String DEFAULT_ALARM_FILE_PATH = "resources/audio/alarm.wav";
	final private static  String DEFAULT_SNOOZE_FILE_PATH = "resources/audio/snooze.wav";

	private PlaySound alarmSound;
	private PlaySound snoozeSound;

	/*
	 * Initialization Methods Block
	 */
	public AlarmSound() {
		this(DEFAULT_ALARM_FILE_PATH, DEFAULT_SNOOZE_FILE_PATH);
	}
	public AlarmSound(String alarmPath, String soozePath) {
		this(new File(alarmPath), new File(soozePath));
	}
	public AlarmSound(File AlarmFile, File snoozeFile) {
		alarmSound = new PlaySound(AlarmFile);
		snoozeSound = new PlaySound(snoozeFile);
		Thread alarmSounsThread = new Thread(alarmSound);
		Thread snoozeounsThread = new Thread(snoozeSound);
		alarmSounsThread.start();
		snoozeounsThread.start();
	}
	/*
	 * Private Methods Block
	 */
	
	/*
	 * Public Methods Block
	 */
	
	/**
	 *	Command Alarm sound thread object to play sound
	 * 
	 */
	public void playAlarm() {
		alarmSound.startAutio();
	}
	/**
	 *	Command Snooze sound thread object to play sound
	 * 
	 */
	public void playSnooze() {
		snoozeSound.startAutio();
	}
	
	/**
	 * Cleaning up opened threads
	 * 
	 */
	public void end() {
		alarmSound.end();
		snoozeSound.end();
	}
	/*
	 * Setter/getter block
	 */

}
