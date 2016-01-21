/**
 * 
 */
package freeman.buyn.timecraft.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Class for log debug info
 * 0 0ff
 * 1 log
 * 2 info
 * 3 warning
 * 4 error
 * @author BuYn
 *
 */
public class DebugMsg {
	private static boolean debugMode = true;
	private static int debugInfoLever = 0;
	private static StringBuffer debugLog = new StringBuffer("Bagen of Log \n");

	/*
	 * Initialization Methods Block
	 */

	/*
	 * Private Methods Block
	 */
	private static void logMsg(String textMesage, int type) {
		debugLog.append("\n" + type);
		debugLog.append(" : " + textMesage);
		if (type<=debugInfoLever) {
			System.out.println(type + " : " + textMesage);
		} 
	}
	/*
	 * Public Methods Block
	 */

	/**
	 * Out and Loging debug masage Warning type
	 * @param textMesage to log
	 */
	public static void debugWarning(String textMesage) {
		if (!debugMode) return;
		logMsg(textMesage, 20);
	}
	/**
	 * Out and Loging debug masage Info type
	 * @param textMesage to log
	 */
	public static void debugInfo(String textMesage) {
		if (!debugMode) return;
		logMsg(textMesage, 30); 
	}
	
	/**
	 * Out and Loging debug masage smoll Log type
	 * @param textMesage to log
	 */
	public static void debugLog(String textMesage) {
		if (!debugMode) return;
		logMsg(textMesage, 40); 
	}
	
	/**
	 * Exception loging and dialog show 
	 * @param exception to Show
	 */
	public static void debugExeption(Exception exception) {
		exception.printStackTrace();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception!!!");
		alert.setHeaderText(exception.getLocalizedMessage());
		alert.setContentText(exception.getMessage());
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();
		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}
	
	/*
	 * Setter/getter block
	 */
	public static void setDebugMode(int newMode) {
		debugInfoLever = newMode;
		debugInfo("Debug info level set to " + newMode);
		if (newMode == 0) 	debugMode = false;
		else debugMode = true;
	}
	
}
