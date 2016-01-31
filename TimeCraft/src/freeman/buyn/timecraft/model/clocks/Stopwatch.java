/**
 * sadull obecy j
 */
package freeman.buyn.timecraft.model.clocks;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;
import java.util.concurrent.atomic.AtomicBoolean;
import freeman.buyn.timecraft.view.AlarmStopwatchController;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Runnable class is wrapper for use button and label of stop watch mechanic 
 * for AlarmStopwatchController class
 * @author BuYn
 *
 */
public class Stopwatch extends Timer implements Runnable {
	
	private AlarmStopwatchController alarmStopwatchController;
    private String labelTime;
    private String labelText = "Time: ";
    private int progressMax;
    private int progressNow;
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
    */
    private Task<Integer> progressIndicatorTask = new Task<Integer>() {			
		@Override
		protected Integer call() throws Exception {
			while (!isCancelled()) {
				updateProgress(progressNow, progressMax);
				synchronized (labelTextIsChanged) {
					try {labelTextIsChanged.wait();
					} catch ( InterruptedException i) {
						debugLog("in exeption InterruptedException progressIndicatorTask");
						Thread.currentThread().interrupt();
						} catch (Exception e) {
							debugLog("in exeption generic progressIndicatorTask");
							// generic exception handling
						    e.printStackTrace();}
				}
			}
    		debugLog("progressIndicatorTask Stopt");
    		return 0;
			// TODO call in Type1454128004039 method stub Auto-generated BuYn30 џэт. 2016 у.6:26:44 
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
	public Stopwatch(AlarmStopwatchController newAlarmStopwatchController) {
		super();
		this.alarmStopwatchController = newAlarmStopwatchController;
		alarmStopwatchController.timeLabel.textProperty().bind(labelTextTask.messageProperty());
		setStartToZero();
		Thread labelTextThread = new Thread(labelTextTask);
		labelTextThread.setDaemon(true);
		labelTextThread.start();
		alarmStopwatchController.timeProgressIndicator.progressProperty().bind(progressIndicatorTask.progressProperty());
		Thread progressIndicatorThread = new Thread(progressIndicatorTask);
		progressIndicatorThread.setDaemon(true);
		progressIndicatorThread.start();
		progressMax = 100;
		runUpdate();
		// TODO constructor Stopwatch stub Auto-generated BuYn25 џэт. 2016 у.3:39:03
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
        debugLog("shuting downs Stopwatch");
        labelTextTask.cancel(true);
        progressIndicatorTask.cancel(true);
        debugLog("Stopwatch end");      
		// TODO run in Stopwatch method stub Auto-generated BuYn23 џэт. 2016 у.23:54:03 
	}

	/*
	* Private Methods Block
	*/
	private void setTextToButton(String newText) {   
		 Platform.runLater(new Runnable() {
            @Override public void run() {
            	alarmStopwatchController.timeButton.setText(newText);
            }
        });
		 
	}
	/*
	 * Public Methods Block
	 */
	/**
	 * Pause timer and set text to button 
	 */
	public void pauseStopWatch() {		
        setTextToButton("Start Stopwatch");
        setPause();
	}

	/**
	 * unPause timer and set text to button 
	 */
	public void unpauseStopWatch() {
        unPause();
        setTextToButton("Pause Stopwatch");
	}
	/**
	 * trigger of Pause state 
	 */
	public void triggerStopWatch() {
        if (pause) {
        	unpauseStopWatch();
        }else
        	pauseStopWatch(); 
	}
	/**
	 * Say to thread to shutdown
	 * and shutdown all open tasks
	 */
	public void shutdown() {
		shutdown  = true;
	}
	/**
	 * StopWatch reset starting time point to now  moment
	 * delta time now Zero
	 */
	public void resetStopWatch(){
    	setStartToZero();
    	progressNow =0;
        runUpdate();
    }
	/**
	 * Set actual time and progress position 
	 * say to Worker to stop waiting  
	 */
	public void runUpdate() {
		labelTime = getFormatDeltaTime();
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
	public int progressNow() {
		progressNow++;
		return progressNow;
		// TODO progressNow in Stopwatch method stub Auto-generated BuYn30 џэт. 2016 у.7:43:41 
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
