/**
 * 
 */
package freeman.buyn.timecraft.view;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;

import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.Person;
import freeman.buyn.timecraft.model.clocks.AlarmClock;
import freeman.buyn.timecraft.model.clocks.ExtendPanel;
import freeman.buyn.timecraft.model.clocks.Stopwatch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Controler for for AlarmStopwatch.fxml
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
	public Label 	alarmSetLabel;
	public int 		alarmSet = 3;
	@FXML
	public ProgressBar alrmProgressBar;
	@FXML
	public ProgressIndicator timeProgressIndicator;
	@FXML
	public Button timeButton;
	@FXML
	public Button alarmButton;
	@FXML
	public BorderPane mainBorderPanel;
	Spinner<Integer> spinner;
	//global Integers
	public Person selectedActivitiData;	
	private Stopwatch stopwatch;
	private AlarmClock alarmClock;
	private ExtendPanel extendPanel;
	

	/*
	 * Initialization Methods Block
	 */
	/**
	 * 
	 */
    public AlarmStopwatchController() {
    	
    	// TODO constructor AlarmStopwatchController stub Auto-generated BuYn23 ÿםג. 2016 ד.8:08:31
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
	
	private int openSetAlarmMenu() {
		final ContextMenu cm = new ContextMenu();
		MenuItem cmItem1 = createMenuItem("mNext", "Next Long Option",t->System.out.println("next"));
	    MenuItem cmItem2 = createMenuItem("mBack", "Go Back", t->System.out.println("back"));
	    Slider slider = new Slider(0, 10, alarmSet % 10);
	    slider.valueProperty().addListener(t->{
	    	debugLog("slider value - " + slider.getValue());
	    	spinner.getValueFactory().setValue((int) (alarmSet - (alarmSet % 10) + slider.getValue()));
	    });
	    CustomMenuItem customMenuItem = new CustomMenuItem(slider);
        customMenuItem.setHideOnClick(false);
        spinner = new Spinner<Integer>(1 , 9999, alarmSet , 10);
        spinner.setEditable(true);
        spinner.valueProperty().addListener( (obs, oldValue, newValue) ->{
        	debugLog("new Value - " + newValue.toString());
        	setAlarm(newValue);
        	this.alarmSet = newValue; 
        	slider.setValue(newValue % 10);
        	alarmSetLabel.setText(newValue.toString() + "m");
        	});
        CustomMenuItem spinerField = new CustomMenuItem(spinner);        																		
        spinerField.setHideOnClick(false);
        //cm.getItems().add(customMenuItem);
	    cm.getItems().addAll(cmItem1,cmItem2 , spinerField, customMenuItem);
	    cm.show(alarmSetLabel, (alarmSetLabel.getLayoutX() + alarmSetLabel.getScene().getWindow().getX() + alarmSetLabel.getScene().getX()), (alarmSetLabel.getLayoutY()+ alarmSetLabel.getScene().getWindow().getY()+ alarmSetLabel.getScene().getY()));
		return 3;
		// TODO openSetAlarmMenu in AlarmStopwatchController method stub Auto-generated BuYn27 לאנעא 2016 ד.5:49:05 
	}
	private void setAlarm(int newAlarmSet) {
		alarmSet = newAlarmSet;
		alarmClock.setAlarmSetingMinuts(newAlarmSet);
		alarmClock.resetAlarmClock();
		alarmClock.unpauseAlarmClock();
		// TODO setAlarm in AlarmStopwatchController method stub Auto-generated BuYn28 לאנעא 2016 ד.11:24:32 
	}
	private MenuItem createMenuItem(String symbol, String text, EventHandler<ActionEvent> t){
	    MenuItem m = new MenuItem(text);
	    StackPane g = new StackPane();
	    g.setPrefSize(24, 24);
	    g.setId(symbol);
	    m.setGraphic(g);
	    m.setOnAction(t);
	    return m;
	}
	
	/*
	 * Public Methods Block
	 */
	@FXML
	public void handleAlarmlSetLeftClik() {
		alarmClock.setAlarmSetingMinuts(alarmSet);
		alarmClock.resetAlarmClock();
		alarmClock.unpauseAlarmClock();
		debugLog("handleAlarmlSet");
		// TODO in handleAlarmlButton add save too beck up in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.7:37:52 
	}
	@FXML
	public void handleAlarmlSetRightClik() {
		openSetAlarmMenu();
		debugLog("handleAlarmlSet");
		// TODO in handleAlarmlButton add save too beck up in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.7:37:52 
	}


	@FXML
	public void handleResetAllButton() {
		stopwatch.resetStopWatch();
		alarmClock.resetAlarmClock();
		stopwatch.unpauseStopWatch();
		alarmClock.unpauseAlarmClock();
		// TODO hahdleResetAllButton in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.7:31:56 
	}
	@FXML
	public void handleRighteExtendButton() {
		if (extendPanel == null) {
			extendPanel = new ExtendPanel(this); 
		}
		debugLog("log Massage :" + " handleRighteExtendButton");
		// TODO handleRighteExtendButton in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.7:37:41 
	}
	@FXML
	public void handleStopwatchButton() {
		stopwatch.triggerStopWatch();		
		// TODO save value to disk as backup.xml file BuYn24 ÿםג. 2016 ד.7:37:47 
	}
	@FXML
	public void handleAlarmlButton() {
		alarmClock.triggerAlarmClock();
		// TODO in handleAlarmlButton add save too beck up in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.7:37:52 
	}
	/**
	 * Ending Alarm Stopwatch
	 * and sending message to it modules to shutdown
	 */
	public void shutdown() {
		stopwatch.shutdown();
		alarmClock.shutdown();
		debugLog("log Massage :" + "AlarmStopwatch Close");
		//TODO handle in Class on open threads       22:09:56 29 ÿםג. 2016 ד. by BuYn
	}
	
	/*
	 * Setter/getter block
	 */

	public void setActivitiData(Person selectedActivitiData) {
		this.selectedActivitiData = selectedActivitiData;
		// TODO setPersonData in AlarmStopwatchController method stub Auto-generated BuYn24 ÿםג. 2016 ד.8:58:35 
	}

}
