/**
 * 
 */
package freeman.buyn.timecraft.view;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;

import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.Person;
import freeman.buyn.timecraft.model.clocks.AlarmClock;
import freeman.buyn.timecraft.model.clocks.Stopwatch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * Controler for for AlarmStopwatchController.fxml
 * @author BuYn
 *
 */

public class AlarmStopwatchController implements FXmlControler{
	
	//fxml fields block
	@FXML
	public Label timeLabel; 
	@FXML
	public Label alarmLabel;
	@FXML
	public ProgressBar alrmProgressBar;
	@FXML
	public ProgressIndicator timeProgressIndicator;
	@FXML
	public Button timeButton;
	@FXML
	public Button alarmButton;
	//global Integers
	public Person selectedActivitiData;	
	private Stopwatch stopwatch;
	private AlarmClock alarmClock;

	/*
	 * Initialization Methods Block
	 */
	/**
	 * 
	 */
    public AlarmStopwatchController() {
    	
    	// TODO constructor AlarmStopwatchController stub Auto-generated BuYn23 џэт. 2016 у.8:08:31
    }
    
	@FXML
	public void initialize(){
		alarmClock = new AlarmClock(this);   	
    	stopwatch = new Stopwatch(this);
    	Thread stopwatchThread = new Thread(stopwatch);
		Thread alarmClockThread = new Thread(alarmClock);
		stopwatchThread.setDaemon(true);
		alarmClockThread.setDaemon(true);
        stopwatchThread.start();
        alarmClockThread.start();
	}	
	/*
	 * Private Methods Block
	 */

	/*
	 * Public Methods Block
	 */
	@FXML
	public void handleResetAllButton() {
		stopwatch.resetStopWatch();
		alarmClock.resetAlarmClock();
		stopwatch.unpauseStopWatch();
		alarmClock.unpauseAlarmClock();
		debugLog("log Massage :" + " handleResetAllButton");
		// TODO hahdleResetAllButton in AlarmStopwatchController method stub Auto-generated BuYn24 џэт. 2016 у.7:31:56 
	}
	@FXML
	public void handleRighteExtendButton() {
		stopwatch.progressNow();
		debugLog("log Massage :" + " handleRighteExtendButton");
		// TODO handleRighteExtendButton in AlarmStopwatchController method stub Auto-generated BuYn24 џэт. 2016 у.7:37:41 
	}
	@FXML
	public void handleStopwatchButton() {
		debugLog("log Massage :" + " handleStopwatchButton");
		stopwatch.triggerStopWatch();		
		// TODO save value to disk as backup.xml file BuYn24 џэт. 2016 у.7:37:47 
	}
	@FXML
	public void handleAlarmlButton() {
		alarmClock.triggerAlarmClock();
		debugLog("log Massage :" + " handleAlarmlButton");		
		// TODO in handleAlarmlButton add save too beck up in AlarmStopwatchController method stub Auto-generated BuYn24 џэт. 2016 у.7:37:52 
	}
	public void shutdown() {
		stopwatch.shutdown();
		
		debugLog("log Massage :" + "AlarmStopwatch Close");
		//TODO handle in Class on open threads       22:09:56 29 џэт. 2016 у. by BuYn
	}
	/*
	 * Setter/getter block
	 */

	public void setActivitiData(Person selectedActivitiData) {
		this.selectedActivitiData = selectedActivitiData;
		// TODO setPersonData in AlarmStopwatchController method stub Auto-generated BuYn24 џэт. 2016 у.8:58:35 
	}

}
