/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import static freeman.buyn.timecraft.util.DebugMsg.debugInfo;

import freeman.buyn.timecraft.view.AlarmStopwatchController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * @author BuYn
 *
 */
public class ExtendPanel {

	private AlarmStopwatchController alarmStopwatchController ;
	private BorderPane mainPanel;

	public ExtendPanel(AlarmStopwatchController alarmStopwatchController) {
		this.alarmStopwatchController = alarmStopwatchController;
		this.mainPanel = alarmStopwatchController.mainBorderPanel;
		creatPanel();
		// TODO constructor ExtendPanel stub Auto-generated BuYn6 марта 2016 г.23:00:31
	}

	private void creatPanel() {
		//BorderPane
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane =		new AnchorPane();
		anchorPane.setTopAnchor(borderPane, null);
//				borderPane.set
		//anchorPane.setMinHeight(500);
		anchorPane.setMinWidth(200);
		//anchorPane.setMinSize(1000, 1000);
		
		
		mainPanel.setRight(anchorPane);
		debugInfo("Info Massage :" + " Panel Create");
		// TODO creatPanel in ExtendPanel method stub Auto-generated BuYn6 марта 2016 г.23:07:12 
	}

	/*
	 * Initialization Methods Block
	 */

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
