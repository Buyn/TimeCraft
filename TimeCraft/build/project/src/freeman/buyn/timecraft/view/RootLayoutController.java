/**
 * 
 */
package freeman.buyn.timecraft.view;

import static freeman.buyn.timecraft.util.DebugMsg.debugLog;

import java.io.File;

import freeman.buyn.timecraft.MainApp;
import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.JAXBWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author BuYn
 *
 */
public class RootLayoutController implements FXmlControler{
	 // Reference to the main application
	private MainApp mainApp;

	/*
	 * Began of Initialization Block
	 * 
	 */
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize(){
		
		System.out.println("Initilaz RootLayoutController End");
	}
	/**
	 * default Constructor
     * The constructor is called before the initialize() method.
	 */
	public RootLayoutController() {
		System.out.println("poc constuctor");
	}
	/*
	 * Private Methods Block
	 */
	
	/**
	 * Create Object of file chooser for dialog open
	 * and creating filter for it 
	 * adding it to chooser,
	 * setting other propertes
	 * @return FileChooser
	 */
	private FileChooser CreateFileChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", 
				"*.xml");
		fileChooser.setSelectedExtensionFilter(extensionFilter);
		File file = mainApp.getPersonFilePath();
		if (file != null) 
			fileChooser.setInitialDirectory(file.getParentFile());
		return fileChooser;
	}
	/*
	 * Public Methods Block
	 */
    /**
     * Creates an empty address book.
     */
	@FXML
	public void handleNewClick() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null); 
	}

	/**
     * Saves the file to the person file that is currently open. 
     * If there is no open file, the "save as" dialog is shown.
     * 
     */
	@FXML
	public void handleSaveClick() {
		File fileToSave = mainApp.getPersonFilePath();
		if (fileToSave == null) handleSaveAsClick();
		JAXBWrapper listWrapper = new JAXBWrapper(mainApp.getPersonData());
		listWrapper.savePersonDataToFile(fileToSave);
	}
    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
	@FXML
	public void handleSaveAsClick() {
		File fileToSave = CreateFileChooser().showSaveDialog(mainApp.getPrimaryStage());
		if (fileToSave == null) return;
		JAXBWrapper listWrapper = new JAXBWrapper(mainApp.getPersonData());
		fileToSave = listWrapper.savePersonDataToFile(fileToSave);
		//save to OS
		if (fileToSave != null) 
			mainApp.setPersonFilePath(fileToSave);
	}
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
	@FXML
	public void handleLoadClick() {
		File fileForLoad = CreateFileChooser().showOpenDialog(mainApp.getPrimaryStage());
		if (fileForLoad == null)	return;
		JAXBWrapper listWrapper = new JAXBWrapper(mainApp.getPersonData());
		listWrapper.loadPersonDataFromFile(fileForLoad);
		//save to OS
		if (fileForLoad != null) 
			mainApp.setPersonFilePath(fileForLoad);	
	}
    /**
     * Closes the application.
     */
	@FXML
	public void handleExitClick() {
		System.exit(0);; 
	}
    /**
     * Opens an about dialog.
     */
	@FXML
	public void handleAboutClick() {
		Alert about = new Alert(AlertType.INFORMATION);
		about.setTitle("About");
		about.setHeaderText("TimeCraft");
		about.setContentText(	"Autor BuYn \n" + 
				"email: buyn.java@gmail.com \n" + 
				"version 0.1.0");
		about.showAndWait(); 
	}
	/**
	 * Show Statistic Chart
	 */
	@FXML
	public void handleStatisticClick() {
		mainApp.showStatistics();
	}
		/*
		 * Began of setter/getter block
		 * 
		 */
	  /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	}
