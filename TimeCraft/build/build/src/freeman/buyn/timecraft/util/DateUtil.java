/**
 * 
 */
package freeman.buyn.timecraft.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Helper functions for handling dates.
 * 
 * @author BuYn
 */
public class DateUtil {
	/**
	 * Adapter (for JAXB) to convert between the LocalDate and the ISO 8601 
	 * String representation of the date such as '2012-12-03'.
	 * 
	 */
	public static class LocalDateAdapter extends XmlAdapter<String, LocalDate>{
		/** 
		 * Unmarshal form string to LocalDate
		 * using parser from DateUtil class
		 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
		 */
		@Override
		public LocalDate unmarshal(String v) throws Exception {
			return parse(v);
		}
		/** 
		 * Marshal to string from LocalDate
		 * using toString from DateUtil class
		 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
		 */
		@Override
		public String marshal(LocalDate v) throws Exception {
			return DateUtil.toString(v); 
		}
		
	}
	/** The date pattern that is used for conversion. Change as you wish. */
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	/** The date formatter. */
	private static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
    /**
     * Returns the given date as a well formatted String. The above defined 
     * {@link DateUtil#DATE_PATTERN} is used.
     * 
     * @param date the date to be returned as a string
     * @return formatted string
     */
	public static final String toString(LocalDate converDate){
		if (converDate == null) {
			return null;
		}
		return DATE_FORMATER.format(converDate);
	}
    /**
     * Returns the given String as a well formatted String. The above defined 
     * 
     * @param string returned as a string
     * @return formatted string
     */
	public static final String toString(String toString){
		return toString;
	}
    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN} 
     * to a {@link LocalDate} object.
     * 
     * Returns null if the String could not be converted.
     * 
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
	public static final LocalDate parse(String stringToConvert){
		try {
			return DATE_FORMATER.parse(stringToConvert, LocalDate::from);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    /**
     * Checks the String whether it is a valid date.
     * 
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return parse(dateString) != null;
    }
}
