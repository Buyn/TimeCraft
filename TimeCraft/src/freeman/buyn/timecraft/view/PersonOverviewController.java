/**
 * 
 */
package freeman.buyn.timecraft.view;

import static freeman.buyn.timecraft.util.DebugMsg.debugInfo;

import freeman.buyn.timecraft.MainApp;
import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.Person;
import freeman.buyn.timecraft.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller Class for {@link freeman.buyn.timecraft.view.PersonOverview.fxml}
 * @author BuYn
 *
 */
public class PersonOverviewController implements FXmlControler {
	@FXML
	private TableView <Person> personTable;
	@FXML
	private TableColumn <Person, String> firstNameColumn;
	@FXML
	private TableColumn <Person, String> lastNameColumn;
	// INFO fields of right panel   
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    // Reference to the main application.
    private MainApp mainApp;
    /*
     * Initialization Block
     */
	/**
	 * default Constructor
     * The constructor is called before the initialize() method.
     */
	public PersonOverviewController() {
		System.out.println("poc constuctor");
	}
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize(){
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData->cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData->cellData.getValue().lastNameProperty());
		showPersonDetails(null);
		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue)->
					showPersonDetails(newValue));
		debugInfo("Info Massage :" + " Initialize PersonOverviewController End");
	}	
	/*
	 * Methods Block
	 */
	/**
	 * Fills all text fields to show details about the person.
	 * If the specified person is null, all text fields are cleared.
	 * 
	 * @param person the person or null
	 */
	private void showPersonDetails(Person showPerson) {
		if (showPerson == null) {
	        // Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			cityLabel.setText("");
			postalCodeLabel.setText("");
			birthdayLabel.setText("");
		}else{
			firstNameLabel.setText(showPerson.getFirstName());
			lastNameLabel.setText(showPerson.getLastName());
			streetLabel.setText(showPerson.getStreet());
			cityLabel.setText(showPerson.getCity());
			postalCodeLabel.setText(Integer.toString(showPerson.getPostalCode()));
			birthdayLabel.setText(DateUtil.toString(showPerson.getBirthday()));
		}
	}	
	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 * 
	 * @return What button presed true for OK
	 */
	@FXML
	public void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			if (mainApp.showPersonEditDialog(selectedPerson)) {
				showPersonDetails(selectedPerson);
			}else 	return;
			
		}else {
			Alert warning = new Alert(AlertType.WARNING);
			warning.initOwner(mainApp.getPrimaryStage());
	        warning.setTitle("No Selection");
	        warning.setHeaderText("No Person Selected");
	        warning.setContentText("Please select a person in the table.");
	        warning.showAndWait();
		}
		// TODO Do it as independent thread
	}
	 /**
	 * Called when the user clicks the new button. Opens a dialog to edit
 	 * details for a new person.
 	 * @return What button presed true for OK
	 */
	@FXML
	public void handleNewPerson() {
		Person temPerson = new Person();
		if(mainApp.showPersonEditDialog(temPerson)) personTable.getItems().add(temPerson);
		// TODO Do new dialog as independent thread
	}
	/**
	 * Called when the user clicks on the delete button.
	 * @param delPerson {@link Person} to delete
	 * 
	 */
	@FXML
	public void handleDeletePerson() { 
		//TODO Add dialog of comformation 
		int indexToDelate= personTable.getSelectionModel().getSelectedIndex();
		if (indexToDelate >= 0) personTable.getItems().remove(indexToDelate);
		else {
			// Nothing selected. Open Alert Windows
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");
			//show alert window
			alert.showAndWait();
		}
	}
	/**
	 * Called when the user clicks on the Start button.
	 * 
	 */
	@FXML
	public void handleStartButton() { 
		Person selectedActiviti = personTable.getSelectionModel().getSelectedItem();
		if (selectedActiviti != null) 
			mainApp.runAlarmStopWatch(selectedActiviti);	
		else {
			Alert warning = new Alert(AlertType.WARNING);
			warning.initOwner(mainApp.getPrimaryStage());
	        warning.setTitle("No Selection");
	        warning.setHeaderText("Activiti Not Selected");
	        warning.setContentText("Please select a activiti in the table.");
	        warning.showAndWait();
		}
	}

	/**
     * Is called by the main application to give a reference back to itself.
     * Setim from FXML controler loader 
     * in mainAPP class
	 * @param mainApp the mainApp to set
	 */
	public void setMainApp(MainApp newMainApp) {
		this.mainApp = newMainApp;
		System.out.println("seting Items");
		// Add observable list data to the table
		personTable.setItems(this.mainApp.getPersonData());
	}
}
