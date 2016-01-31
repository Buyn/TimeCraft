/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;
import java.util.concurrent.atomic.AtomicBoolean;
import freeman.buyn.timecraft.model.clocks.Alarm;
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
    private int minutsFromSetoff =0;
    private long progressMax;
    private long progressNow;
    //using AtomicBoolean to be shuer that object link, will not change. used to sinchronize same object
    private final AtomicBoolean labelTextIsChanged = new AtomicBoolean(true);
    private boolean shutdown =false;
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
			// TODO call in Type1454128004039 method stub Auto-generated BuYn30 ���. 2016 �.6:26:44 
		}
	}; 

	/*
	* Initialization Methods Block
	*/
	
	/**
	 * on start binding label and  progress 
	 * and start workers who updates binds
	 * seting up timer
	* @param newAlarmStopwatchController
	*/
	public AlarmClock(AlarmStopwatchController newAlarmStopwatchController) {
		super(1);
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
	}
	/**
	 * waking up in each secund to run Update 
	 * in shutting  - canceling workers
	* @see java.lang.Runnable#run()
	*/
	@Override
	public void run() {
        while (!shutdown) {
           if (!pause) runUpdate();
           try {
        	   Thread.sleep(Timer.SECONDS);}catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        }
        debugLog("shuting downs AlarmClock");
        labelTextTask.cancel(true);
        progressTask.cancel(true);
        debugLog("AlarmClock end");      
		// TODO run in AlarmClock method stub Auto-generated BuYn23 ���. 2016 �.23:54:03 
	}
	/*
	* Private Methods Block
	*/
	private void setTextToButton(String newText) {   
		 Platform.runLater(new Runnable() {
            @Override public void run() {
            	alarmStopwatchController.alarmButton.setText(newText);
            }
        });
		 
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
	 * trigger of Pause state 
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
	 * delta time now Zero
	 */
	public void resetAlarmClock(){
    	setStartToZero();
    	minutsFromSetoff = 0;
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
		labelTime = getFormatTimeLeft();
		progressNow = progressNow();
        synchronized (labelTextIsChanged) {
        	labelTextIsChanged.set(true);
        	labelTextIsChanged.notifyAll();
		}
	}	
	/**
	 * Calculate new progress position
	 * @return progress position
	 */
	public Long progressNow() {
		return progressMax - getTimeLeft();
		// TODO progressNow in AlarmClock method stub Auto-generated BuYn30 ���. 2016 �.7:43:41 
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
