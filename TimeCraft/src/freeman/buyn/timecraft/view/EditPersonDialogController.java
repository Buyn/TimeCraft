/**
 * 
 */
package freeman.buyn.timecraft.view;

import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.Person;
import freeman.buyn.timecraft.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a person.
 * Controller Class for {@link freeman.buyn.timecraft.view.EditPersonDialog.fxml}
 * @author BuYn
 * 
 */
public class EditPersonDialogController implements FXmlControler{
	// INFO fields of right panel   
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    // Reference to the main application.
	private Stage editStage;
	private Person personToEdit;
	private Boolean okClicked = false;
    /*
     * Initialization Block
     */
	/**
	 * default Constructor
     * The constructor is called before the initialize() method.
     */
	public EditPersonDialogController() {
		System.out.println("poc constuctor");
	}
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize(){
		// Initialize the person table with the two columns.

		// Listen for selection changes and show the person details when changed.

	}	
	/*
	 * Private Methods Block
	 */
	/**
	 * Fill in date to Person for TextField
	 * of dialog
	 */
	private void applaingTextFields() { 
		personToEdit.setFirstName(firstNameField.getText());
		personToEdit.setLastName(lastNameField.getText());
		personToEdit.setStreet(streetField.getText());
		personToEdit.setPostalCode(postalCodeField.getText());
		personToEdit.setCity(cityField.getText());
		personToEdit.setBirthday(DateUtil.parse(birthdayField.getText()));
	}
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
	private boolean isInputValid() { 
		StringBuffer errors = new StringBuffer();
		if (firstNameField.getText().length()== 0) errors.append("No valid first name!\n");              
		if (lastNameField.getText().length()== 0) errors.append("No valid last name!\n");              
		if (streetField.getText().length()== 0) errors.append("No valid street!\n");             
		if(		postalCodeField.getText() == null ||
				postalCodeField.getText().length()== 0 )
			errors.append("No valid postal code\n"); 
		else {
			try {Integer.parseInt(postalCodeField.getText());}
			catch (Exception e) {errors.append("No valid postal code (must be an integer)!\n");}
		} 
		if (cityField.getText().length()== 0) errors.append("No valid city!\n");             
		if (!DateUtil.validDate(birthdayField.getText())) errors.append("No valid birthday. Use the format dd.mm.yyyy!\n");             
		if (errors.length()== 0) {
			return true;
		} else {
			// Show the error message.
			Alert warning = new Alert(AlertType.WARNING);
			warning.initOwner(editStage);
			warning.setTitle("Invalid Fields");
			warning.setHeaderText("Please correct invalid fields");
			warning.setContentText(errors.toString());
			warning.showAndWait();
			return false;
		}
	}
	/*
	 * Public Methods Block
	 */
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	public void handleOkClick() {
		if (isInputValid()) {
			applaingTextFields(); 
			okClicked = true;
			editStage.close();		
		}
	}
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	public void handleCancelClick() {
		okClicked = false;
		editStage.close();
	}
	/**
	 * Called when the user clicks Apply.
	 */
	@FXML
	public void handleApplyClick() {
		if (isInputValid())	applaingTextFields();		
	}
	
	/*
	 * Began of setter\getter block
	 * 
	 */
	/**
     * Sets the person to be edited in the dialog and inits texts Felds 
     * 
	 * @param newPersonToEdit Person to set
	 */
	public void setPerson(Person newPersonToEdit) {
		this.personToEdit = newPersonToEdit;
		if (personToEdit == null) {
	        // Person is null, remove all the text.
			firstNameField.setText("");
			lastNameField.setText("");
			streetField.setText("");
			cityField.setText("");
			postalCodeField.setText("");
			birthdayField.setText("");
		}else{
			firstNameField.setText(personToEdit.getFirstName());
			lastNameField.setText(personToEdit.getLastName());
			streetField.setText(personToEdit.getStreet());
			cityField.setText(personToEdit.getCity());
			postalCodeField.setText(Integer.toString(personToEdit.getPostalCode()));
			birthdayField.setText(DateUtil.toString(personToEdit.getBirthday()));
		}
	}
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
	public void setStage(Stage newEditStage) {
		this.editStage = newEditStage; 	
	}
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
	public Boolean isOKPressed() { 
		return okClicked;
	}
}
