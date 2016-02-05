/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;
import java.util.concurrent.atomic.AtomicBoolean;
import freeman.buyn.timecraft.model.clocks.Alarm;
import freeman.buyn.timecraft.model.clocks.AlarmSound;
import freeman.buyn.timecraft.view.AlarmStopwatchController;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Runnable class is wrapper for use button and label of Alarm clock  mechanic 
 * for AlarmStopwatchController class
 * @author BuYn
 *
 */
public class AlarmClock extends Alarm implements Runnable{
	
	private AlarmStopwatchController alarmStopwatchController;
    private String labelTime;
    private String labelText = "Alarm: ";
    private int snoozeCounter =0;
    private long progressMax;
    private long progressNow;
    //using AtomicBoolean to be suer that object link, will not change. used to synchronize same theads
    private final AtomicBoolean labelTextIsChanged = new AtomicBoolean(true);
    private boolean shutdown =false;
    private AlarmSound alarmSound;
    /**
     * Workers for Time label of controller binding updated messages 	
     */
    private Task<String> labelTextTask=new Task<String>(){
    	@Override
    	protected String call() {
    		while (!isCancelled()) {
				synchronized (labelTextIsChanged) {
					if (labelTextIsChanged.get()) {
						updateMessage(labelText + labelTime);
						labelTextIsChanged.set(false);;
					}
					try {labelTextIsChanged.wait();
					} catch ( InterruptedException i) {
						debugLog("in exeption InterruptedException labelTextTask");
						Thread.currentThread().interrupt();
						} catch (Exception e) {
							debugLog("in exeption generic labelTextTask");
							// generic exception handling
						    e.printStackTrace();}
				}
			}
    		debugLog("labelTextTask Stopt");
    		return "Work Stopt";
    	}
    };
    /**
    * Workers for progress Indicator of controller,  updating Progress 
    * not using boolean chek on chenge,  and can be show mistake position
    * when on pause get exception
    */
    private Task<Integer> progressTask = new Task<Integer>() {			
		@Override
		protected Integer call() throws Exception {
			while (!isCancelled()) {
				updateProgress(progressNow, progressMax);
				synchronized (labelTextIsChanged) {
					try {labelTextIsChanged.wait();
					} catch ( InterruptedException e) {
						debugLog("in exeption InterruptedException progressTask");
						Thread.currentThread().interrupt();
						} catch (Exception e) {
							debugLog("in exeption generic progressTask");
							// generic exception handling
						    e.printStackTrace();}
				}
			}
    		debugLog("progressTask Stopt");
    		return 0;
		}
	}; 

	/*
	* Initialization Methods Block
	*/
	
	/**
	 * on start binding label and  progress 
	 * and start workers who updates binds
	 * Setting up timer
	* @param newAlarmStopwatchController
	*/
	public AlarmClock(AlarmStopwatchController newAlarmStopwatchController) {
		super(9);
		this.alarmStopwatchController = newAlarmStopwatchController;
		alarmStopwatchController.alarmLabel.textProperty().bind(labelTextTask.messageProperty());
		setStartToZero();
		Thread labelTextThread = new Thread(labelTextTask);
		labelTextThread.setDaemon(true);
		labelTextThread.start();
		alarmStopwatchController.alrmProgressBar.progressProperty().bind(progressTask.progressProperty());
		Thread progressThread = new Thread(progressTask);
		progressThread.setDaemon(true);
		progressThread.start();
		resetAlarmClock();
		//init Sound Alarms audio Fills in Sounds threads
		alarmSound = new AlarmSound();
	}
	/**
	 * waking up in each second to run graphic Updates.
	 * On shutting  - canceling running workers threads
	* @see java.lang.Runnable#run()
	*/
	@Override
	public void run() {
        while (!shutdown) {
           if (!pause) {
        	   if(isAlarm())setoffAlarm();
        	   runUpdate();
           }
           try {Thread.sleep(Timer.SECONDS);}
           catch (InterruptedException ex) {
        	   Thread.currentThread().interrupt();}
        }
        debugLog("shuting downs AlarmClock");
        labelTextTask.cancel(true);
        progressTask.cancel(true);
        alarmSound.end();
        debugLog("AlarmClock end");      
	}

	/*
	* Private Methods Block
	*/
	
	/**
	 * Set new Text to alarm controlling button 
	 * can be used from not fx thread
	 * 
	 * @param newText new Text for alarm button  
	 */
	private void setTextToButton(String newText) {   
		 Platform.runLater(new Runnable() {
            @Override public void run() {
            	alarmStopwatchController.alarmButton.setText(newText);
            }
        });
		 
	}
	/**
	 * Setting off Alarm! Playing sound and snooze timer. 
	 * Additional counting number of snoozers times from first settoff
	 */
	private void setoffAlarm() {
		playSound();
		super.snooze();
		snoozeCounter++;
	}
	/**
	 * Decide on variable, snoozeCounter number, witch sound to play. 
	 * And say Alarm sound object to play it
	 * 
	 */
	private void playSound() {
		if(snoozeCounter==0) alarmSound.playAlarm();
		else alarmSound.playSnooze();
	}
	/*
	 * Public Methods Block
	 */
	/**
	 * Pause timer and set text to button 
	 */
	public void pauseAlarmClock() {		
        setTextToButton("Start Alarm");
        setPause();
	}
	/**
	 * unPause timer and set text to button 
	 */
	public void unpauseAlarmClock() {
        unPause();
        setTextToButton("Pause Alarm");
	}
	/**
	 * trigger of Pause state of Alarm
	 */
	public void triggerAlarmClock() {
        if (pause) {
        	resetAlarmClock();
        }else
        	pauseAlarmClock(); 
	}
	/**
	 * Say to thread to shutdown
	 * and shutdown all open tasks
	 */
	public void shutdown() {
		shutdown  = true;
	}
	/**
	 * AlarmClock reset starting time point to now
	 * delta time now Zero. Update progress calculations and  snooze Counter
	 */
	public void resetAlarmClock(){
    	setStartToZero();
    	snoozeCounter = 0;
    	progressMax = getTimeLeft();
    	progressNow = 0;
    	unpauseAlarmClock();
        runUpdate();
    }
	/**
	 * Set actual time and progress position 
	 * say to Worker to stop waiting  
	 */
	public void runUpdate() {		
		updateText();
		updateProgress();		
        synchronized (labelTextIsChanged) {
        	labelTextIsChanged.set(true);
        	labelTextIsChanged.notifyAll();
		}
	}	
	/**
	 * Calculate new progress position
	 * @return progress position
	 */
	private void updateProgress() {
		progressNow = progressMax - getTimeLeft();
	}	
	/**
	 * New text with actual time for time Label 
	 */
	private void updateText() {
		labelTime = getFormatTimeLeft();
		if(snoozeCounter == 0)labelTime = labelTime + " ";
		else labelTime = labelTime + " (" + snoozeCounter + ")";
	}
	/*
	 * Setter/getter block
	 */
	public String getLabelTime() {
		return labelTime;
	}
	public void setLabelTime(String labelTime) {
		this.labelTime = labelTime;
	}
	public String getLabelText() {
		return labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

}
