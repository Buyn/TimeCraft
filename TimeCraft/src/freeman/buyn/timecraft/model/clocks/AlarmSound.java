/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import java.io.File;
import freeman.buyn.timecraft.util.PlaySound;

/**
 * @author BuYn
 *
 */
public class AlarmSound {
	final private static  String DEFULT_ALARM_FILE_PATH = "resources/audio/alarm.wav";
	final private static  String DEFULT_SNOOZE_FILE_PATH = "resources/audio/snooze.wav";

	private PlaySound alarmSound;

	private PlaySound snoozeSound;

	/*
	 * Initialization Methods Block
	 */
	public AlarmSound() {
		this(DEFULT_ALARM_FILE_PATH, DEFULT_SNOOZE_FILE_PATH);
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
	public void playAlarm() {
		alarmSound.startAutio();
		// TODO playAlarm in AlarmSound method stub Auto-generated BuYn1 февр. 2016 г.19:27:17 
	}
	public void playSnooze() {
		snoozeSound.startAutio();
		// TODO playSnooze in AlarmSound method stub Auto-generated BuYn3 февр. 2016 г.10:38:24 
	}
	public void end() {
		alarmSound.end();
		snoozeSound.end();
		// TODO cancel in AlarmSound method stub Auto-generated BuYn1 февр. 2016 г.19:51:03 
	}
	/*
	 * Setter/getter block
	 */

}
