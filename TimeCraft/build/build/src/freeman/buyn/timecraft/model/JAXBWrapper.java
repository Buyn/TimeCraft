/**
 * 
 */
package freeman.buyn.timecraft.model;

import static freeman.buyn.timecraft.util.DebugMsg.debugExeption;
import static freeman.buyn.timecraft.util.DebugMsg.debugLog;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *  * Helper class to wrap a JAXB fuctions  and iner class wraping list of persons. 
 * This is used for saving the list of persons to XML.
 * 
 * @author BuYn
 *PersonListWrapper
 */

public class JAXBWrapper {
	private final static String EXTENSION = ".xml";
	/**
	 * Helper class to wrap a list of persons. This is used for saving the
	 * list of persons to XML.
	 * 
	 */
	@XmlRootElement(name = "persons")
	public static class PersonListWrapper {
		private List<Person> personsListData;
		public PersonListWrapper(List<Person> personsListData) {
			this.personsListData = personsListData;
		}
		public PersonListWrapper() {
			this.personsListData = null;
		}
		@XmlElement(name="person")
		public List<Person> getPersonsListData() {
			return personsListData;
		}
		public void setPersonsListData(List<Person> personsListData) {
			this.personsListData = personsListData;
		}	 
	}
	private List<Person> personsListData;
	/*
	 * Initialization Methods Block
	 */
	/**
	 * @param personsListData
	 */
	public JAXBWrapper(List<Person> personsListData) {
		super();
		this.personsListData = personsListData;
	}
	/**
	 * @param personsListData
	 */
	public JAXBWrapper(ObservableList<Person> personsListData) {
		this((List<Person>) personsListData);
	}
	/**
	 * @param personsListData
	 */
	public JAXBWrapper() {
		this((List<Person>) null);
	}
	
	/*
	 * Private Methods Block
	 */
	/**
	 * Add extension from EXTENSION {@value EXTENSION} ".xml"
	 * @param fileToValid 
	 * @return File old if end whith extension and new, formatete, if not
	 */
	private File fileValidateExtension(File fileToValid) {
		debugLog("path " + fileToValid.getPath());	
		if (!fileToValid.getName().endsWith(EXTENSION)){
			debugLog("String " + fileToValid.toString());
			return new File(fileToValid.toString() + EXTENSION);
		}
		return fileToValid; 
	}
	/*
	 * Public Methods Block
	 */
	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public File savePersonDataToFile(File fileToSave) {
		try {
			fileToSave = fileValidateExtension(fileToSave);
			JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Marshalling and saving XML to the file.
			marshaller.marshal(new PersonListWrapper(personsListData), fileToSave);
			return fileToSave;
		} catch (JAXBException e) {// catches ANY exception
			debugExeption(e);
	        return null;
		} 
	}
	public void savePersonDataToFile(JAXBWrapper listWrapper, File newFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Marshalling and saving XML to the file.
			marshaller.marshal(listWrapper, newFile);
		} catch (JAXBException e) {// catches ANY exception
			debugExeption(e);
		} 
	}
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 * @return List<Person>
	 */
	public List<Person> loadPersonDataFromFile(File newFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			PersonListWrapper listWrapper = (PersonListWrapper) unmarshaller.unmarshal(newFile);
			personsListData.clear();
			personsListData.addAll(listWrapper.getPersonsListData());
			return listWrapper.getPersonsListData();
		}catch (JAXBException e) {// catches ANY exception
			debugExeption(e);
	        return null;
		}  
	}
	/*
	 * Setter/getter block
	 */
	public List<Person> getPersonsListData() {
		return personsListData;
	}
	public void setPersonsListData(List<Person> personsListData) {
		this.personsListData = personsListData;
	}

}
