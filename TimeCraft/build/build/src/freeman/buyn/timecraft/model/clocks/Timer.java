/**
 * 
 */
package freeman.buyn.timecraft.model.clocks;

import java.text.SimpleDateFormat;

/**
 * main class to watch for time
 * and get time that gone from start in milliseconds and Format
 * @author BuYn
 *
 */
public class Timer {
    //Public Constants
    public static final int SECONDS = 1000;
    public static final int MINUTS = SECONDS * 60;
    public static final int HOURS = MINUTS * 60;
    public static final int UA = HOURS * 2;
    //TODO create UA from system settings it must work on any system       3:09:14 28 џэт. 2016 у. by BuYn
    public static final String SDF_FORMAT = "HH:mm:ss";

    //Private Integers
    private long    lStartTime = 0;
    private long    lPauseTime = 0;
    private SimpleDateFormat sdf;
	protected boolean pause = false;
	/*
	 * Initialization Methods Block
	 */
    public Timer(){
        sdf = new SimpleDateFormat(SDF_FORMAT);
        lStartTime = System.currentTimeMillis();
    }
	/*
	 * Private Methods Block
	 */
    
    /**
     * Convert time milliseconds in long
     * to formated string 
     * @param milesecunds to comvert
     * @return formated string
     */
    final protected String formatLong(long lConvert){
        return sdf.format(lConvert - UA);
    }
    /**
     * get system time in milliseconds
     * @return time in milliseconds in long
     */
    final protected long getNow(){
        return System.currentTimeMillis();
    }
	/*
	 * Public Methods Block
	 */
     /**
      * show how much time has passed from the start in milliseconds
     * @return passed time in milliseconds
     */
    final public long getDeltaTime() {
    	return getNow() + lPauseTime - lStartTime ;
    } 
    /**
    * show how much time has passed from the start in formated string
    * @return passed time in formated string
    */
    final public String getFormatDeltaTime(){
    	return formatLong(getDeltaTime());}

    /**
     * memorize time when pause start  plus how long pauses before 
     */
    final public void setPause(){
        lPauseTime = getNow() + lPauseTime - lStartTime ;
        lStartTime = getNow();
        pause = true;
    }
    /**
     * set start time too now  
     */
    final public void unPause(){
        lStartTime = getNow();
        pause =false;
    }
    /**
     * use parent boolean variable too trigger pause state
     * set time win pause start  and set too now start time 
     */
    final public void triggerPause() {
		if (pause)unPause();
		else setPause();
    }
		
	/*
	 * Setter/getter block
	 */
    public void setStartToZero(){
        lStartTime = getNow();
        lPauseTime = 0;
    } 
    final public void setStart(long dStart) {
        this.lStartTime = dStart;
    }
    final public void setPause(long lPause) {
        this.lPauseTime = lPause;
    }
    final public long getPause() {
        return lPauseTime;
    }
    final public long getStart() {
        return lStartTime;
    }
}

