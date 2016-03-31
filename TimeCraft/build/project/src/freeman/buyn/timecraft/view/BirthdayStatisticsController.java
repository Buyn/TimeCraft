package freeman.buyn.timecraft.view;

import static freeman.buyn.timecraft.util.DebugMsg.debugInfo;
import static freeman.buyn.timecraft.util.DebugMsg.debugLog;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * The controller for the birthday statistics view.
 * @author BuYn
 *
 */
public class BirthdayStatisticsController implements FXmlControler{
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private BarChart<String, Integer> barChart;
	private ObservableList<String> monthNames  =  FXCollections.observableArrayList();
	/*
	 * Initialization Methods Block
	 */
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize(){
		// Get an array with the English month names.
		String[] stringMonthNames = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		// Convert it to a list and add it to our ObservableList of months.
		monthNames.addAll(Arrays.asList(stringMonthNames));
		// Assign the month names as categories for the horizontal axis.
		xAxis.setCategories(monthNames);
		debugInfo("init BirthdayStatisticsController end");
	}
	/*
	 * Private Methods Block
	 */

	/*
	 * Public Methods Block
	 */
	  /**
     * Sets the persons to show the statistics for.
     * 
     * @param personsList
     */
	public void setPersonData(List<Person> personsList) {
		// Count the number of people having their birthday in a specific month.
		int[] monthCount = new int[12];
		for (Person p : personsList) 	{
			monthCount[p.getBirthday().getMonthValue()-1]++;
		}
		// Create a XYChart.Data object for each month. Add it to the series.
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for (int i = 0; i < monthCount.length; i++) {
			series.getData().add(new XYChart.Data<String, Integer>(monthNames.get(i), monthCount[i]));
		}
		barChart.getData().addAll(series);
		debugInfo("set Person Data end");
	}
	/*
	 * Setter/getter block
	 */

}
