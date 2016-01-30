/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import freeman.buyn.timecraft.model.clocks.Alarm;
import freeman.buyn.timecraft.view.AlarmStopwatchController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Runnable class is wrapper for use button and label of Alarm clock  mechanic 
 * for AlarmStopwatchController class
 * @author BuYn
 *
 */
public class AlarmClock extends Alarm implements Runnable{
	private AlarmStopwatchController alarmStopwatchController;



	/*
	 * Initialization Methods Block
	 */
	/**
	 * @param alarmStopwatchController
	 */
	public AlarmClock(AlarmStopwatchController alarmStopwatchController) {
		super();
		this.alarmStopwatchController = alarmStopwatchController;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		// TODO run in AlarmClock method stub Auto-generated BuYn23 џэт. 2016 у.23:56:04 
	}
	/*
	 * Private Methods Block
	 */

	/*
	 * Public Methods Block
	 */

	/*
	 * Setter/getter block
	 */

}
