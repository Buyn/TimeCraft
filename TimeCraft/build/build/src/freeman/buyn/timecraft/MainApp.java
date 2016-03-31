package freeman.buyn.timecraft;

import static freeman.buyn.timecraft.util.DebugMsg.debugInfo;
import static freeman.buyn.timecraft.util.DebugMsg.debugLog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.prefs.Preferences;
import freeman.buyn.timecraft.model.FXmlControler;
import freeman.buyn.timecraft.model.JAXBWrapper;
import freeman.buyn.timecraft.model.Person;
import freeman.buyn.timecraft.view.AlarmStopwatchController;
import freeman.buyn.timecraft.view.BirthdayStatisticsController;
import freeman.buyn.timecraft.view.EditPersonDialogController;
import freeman.buyn.timecraft.view.PersonOverviewController;
import freeman.buyn.timecraft.view.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {
	public static final String TITLE = "TimeCraft";
	public static final int DEBUGMODE = 40;
	//Registry Keys
	public static final String KEY_PATH = "filePath";
	
	private Stage primaryStage;
	private BorderPane rootLayout;  
	//TODO create arreylist with interface CloseOnExit       20:05:30 24 џэт. 2016 у. by BuYn         
    /** 
     * The data as an observable list of Persons.     
     * */
	private ObservableList<Person> activitiData  =  FXCollections.observableArrayList();	
	/**
	 * Inner class to wrapper all function for FXML working
	 * create loader and usages  
	 */
	private class WrapofFXmlLoader {
		FXMLLoader loader;
		
		public WrapofFXmlLoader(String pathToFXML) {
			this.loader =  new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(pathToFXML));
		}
		
		private FXmlControler loadFXmlControler() {
			try {return  loader.getController();		} catch (Exception e) {	
																	e.getCause().printStackTrace();
																	e.printStackTrace();
																	return null;
			}
		}
		/**
		* Load person overview.
		*/
		private Node loadFXML() {
			try{return 	loader.load();			}catch(IOException e){
															e.getCause().printStackTrace();
															System.out.println(e);
															return null;
			}
		}
	}
    /*
     * Initialization Block
     */
	/**
	 * Default constructor for main class
	 * @param primaryStage
	 */
	public MainApp() {
		freeman.buyn.timecraft.util.DebugMsg.setDebugMode(DEBUGMODE);
		debugInfo("begin MainApp Constractor");
		activitiData.add(new Person("Test", "Avtiviyi"));
		activitiData.add(new Person("Select and", "press Delete"));
		activitiData.add(new Person("Or Press New", "For new activiti"));
	}
	public static void main(String[] args) {	
		launch(args);
	}	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(TITLE);
		this.primaryStage.getIcons().add(new Image("file:resources/images/B_Time_Lord_Seal.PNG"));
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
			@Override
			public void handle(WindowEvent event) {
				// TODO handle save  bakup method stub Auto-generated BuYn29 џэт. 2016 у.1:25:54 				
				Platform.exit();
				System.exit(0);}
		});
		//Init Main Window
		initRootLayout();
		showPersonOverview();
		debugInfo("Main fxm's loaded");
		File fileForAutoLoad = getPersonFilePath();
		if (fileForAutoLoad != null) {
			debugInfo("path For Auto Load :" + fileForAutoLoad.getPath());
			JAXBWrapper listWrapper = new JAXBWrapper(getPersonData());
			listWrapper.loadPersonDataFromFile(fileForAutoLoad);	
		}
	}
	/*
	 * Private Methods Block
	 */
	/**
	 * Initializes the root layout and tries to load the last opened
	 * person file.
	 */
	private void initRootLayout() {
		// Load root layout from fxml file.
		WrapofFXmlLoader loaderFXML = new WrapofFXmlLoader("view/RootLayout.fxml");
		rootLayout = (BorderPane) loaderFXML.loadFXML();
		// Show the scene containing the root layout.
		Scene primScen = new Scene(rootLayout);
		primaryStage.setScene(primScen);
		primaryStage.show();
		// Give the controller access to the main app.
		RootLayoutController rootLayoutController = (RootLayoutController) loaderFXML.loadFXmlControler();
		rootLayoutController.setMainApp(this);
	}
	/**
	 * Shows the person overview inside the root layout.
	 */
	private void showPersonOverview() {
		WrapofFXmlLoader loaderFXML = new WrapofFXmlLoader("view/PersonOverview.fxml");
		// Set person overview into the center of root layout
		rootLayout.setCenter((AnchorPane)loaderFXML.loadFXML());
		PersonOverviewController controlerPersonOverview = (PersonOverviewController)loaderFXML.loadFXmlControler();
		// Give the controller access to the main app.
		controlerPersonOverview.setMainApp(this);
	}

	/*
	 * Public Methods Block
	 */
	/**
	 * Shows the Edit dialog for Person
	 * @param selectedItem
	 */
	public Boolean showPersonEditDialog(Person editPerson) {
		 // Create the Edit dialog Person Stage.
		Stage editDialog =new Stage();
		editDialog.setTitle("Edit Person");
		editDialog.initModality(Modality.WINDOW_MODAL);
		editDialog.initOwner(primaryStage);
		WrapofFXmlLoader loaderFXML = new WrapofFXmlLoader("view/EditPersonDialog.fxml");
		editDialog.setScene(new Scene((AnchorPane)loaderFXML.loadFXML()));
		//Controller block
		EditPersonDialogController controlerEditDialog = (EditPersonDialogController)loaderFXML.loadFXmlControler();
		// Give the controller access to the Stage and Person to Edit
		controlerEditDialog.setStage(editDialog);
		controlerEditDialog.setPerson(editPerson);
		// Show the dialog and wait until the user closes it
		editDialog.showAndWait();
		return controlerEditDialog.isOKPressed();
	}
	/**
	 * Opens a dialog to show statistics.
	 */
	public void showStatistics() {
		// Create the Statistic dialog Stage.
		Stage statistikDialog =new Stage();
		statistikDialog.setTitle("Statistic");
		statistikDialog.initModality(Modality.WINDOW_MODAL);
		statistikDialog.initOwner(primaryStage);
		WrapofFXmlLoader loaderFXML = new WrapofFXmlLoader("view/BirthdayStatistics.fxml");
		statistikDialog.setScene(new Scene((AnchorPane)loaderFXML.loadFXML()));
		//Controller block
		BirthdayStatisticsController controlerEditDialog = (BirthdayStatisticsController)loaderFXML.loadFXmlControler();
		// Give the controller access to the  Person 
		controlerEditDialog.setPersonData(activitiData);
		// Show the dialog and wait until the user closes it
		statistikDialog.show();
	}
	/**
	 * Sets the file path of the currently loaded file. 
	 * The path is persisted in the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setPersonFilePath(File pathToSet) {
		Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
		if (pathToSet == null) {
			preferences.remove(KEY_PATH);
			setAppTitle();
			return;
		}
		debugLog(pathToSet.getParent());
		debugLog(pathToSet.getName());
		debugLog(pathToSet.getPath());
		debugLog(pathToSet.getAbsolutePath());
		preferences.put(KEY_PATH , pathToSet.getPath());
		setAppTitle(pathToSet.getName());
	}
	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
		Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
		String filePath = preferences.get(KEY_PATH, null);
		if (filePath == null) {
			return null;
		}
		setAppTitle(filePath);
		debugLog(filePath);
		return new File(filePath); 
	}
	/**
	 *  Update the stage title.
	 * @param newTitle String
	 */
	public void setAppTitle(String newTitle) {
		primaryStage.setTitle(TITLE + " : " + newTitle); 
	}
		/**
		 * load FXML and Shows AlarmStopWatch dialog for activiti
		 * @param selectedActiviti
		 */
		public void runAlarmStopWatch(Person selectedActiviti) {
		// Create the AlarmStopWatch dialog Stage.
		Stage alarmStopWatchDialog =new Stage();
		alarmStopWatchDialog.setTitle("AlarmStopWatch for : " + selectedActiviti.getLastName() + 
				" - " + selectedActiviti.getFirstName());
		alarmStopWatchDialog.initModality(Modality.NONE);
		WrapofFXmlLoader loaderFXML = new WrapofFXmlLoader("view/AlarmStopwatch.fxml");
		alarmStopWatchDialog.setScene(new Scene((AnchorPane)loaderFXML.loadFXML()));
		alarmStopWatchDialog.getIcons().add(new Image("file:resources/images/gear-stopwatch-time-speed.PNG"));
		//Controller block
		AlarmStopwatchController alarmStopWatchControler = (AlarmStopwatchController)loaderFXML.loadFXmlControler();
		// Give the controller access to the  Person 
		alarmStopWatchControler.setActivitiData(selectedActiviti);
		// Show the dialog
		alarmStopWatchDialog.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				alarmStopWatchControler.shutdown();
				// TODO handle in Class on open threads method stub Auto-generated BuYn29 џэт. 2016 у.1:25:54 
			}
		});
		alarmStopWatchDialog.show();
		// TODO runAlarmStopWatch add close and save with MainApp BuYn24 џэт. 2016 у.8:47:34 
	}
	/**
	 * Update the stage title to Null.
	 */
	public void setAppTitle() {
		primaryStage.setTitle(TITLE); 
	}
	/*
	 * Began of setter\getter block
	 * 
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public ObservableList<Person> getPersonData() {		
		return activitiData;
	}


}
