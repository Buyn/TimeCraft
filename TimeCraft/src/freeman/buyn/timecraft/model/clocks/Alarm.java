package freeman.buyn.timecraft.model.clocks;

/**
 * extend timer functions for implement
 * to Setting alarm
 * 
 */
public class Alarm extends Timer {
	public static final int DEFAULT_ALARM_SET = 9;
	public static final int DEFAULT_ALARM_SNOOZE = 1;
    private long alarmSetoffTime= 0;
    private long alarmSetting;   
	/*
	 * Initialization Methods Block
	 */
    /**
     * Null constructor use default settings from DEFAULT_ALARM_SET
     */
    public Alarm(){
    	this(DEFAULT_ALARM_SET);
    }   
    /**
     * Constructor set parameter minutes to set alarm
     * @param iMinuts minutes to set alarm
     */
    public Alarm(int iMinuts) {
    	super();
    	setAlarmSetingMinuts(iMinuts);
    	setAlarmTime();
    }
	/*
	 * Private Methods Block
	 */
    final private int formatToMinuts(long timeInMillSec) {
		return (int) (timeInMillSec/MINUTS);
    }
	/*
	 * Public Methods Block
	 */ 
    /**
     * use parent @see freeman.buyn.timecraft.model.clocks.Timer#setStartToZero() and calculate new setoff time
     * 
     */
    @Override
    final public void setStartToZero() {
        super.setStartToZero();
        setAlarmTime();
    }
    /**
     * setoff time minus now in milliseconds
     * @return milliseconds left
     */
    final public long getTimeLeft(){
        return alarmSetoffTime - super.getNow();
    }
     /**
     * setoff time minus now in seconds
     * @return seconds left
     */
    final public long getSekundsLeft(){
        return (alarmSetoffTime - super.getNow())/SECONDS;
    }
     /**
     * setoff time minus now in milliseconds formate too string 
     * @return milliseconds left formate too string 
     */
    final public String getFormatTimeLeft(){
        return formatLong(getTimeLeft());
    }
    /**
     * setoff time it time of start plus alarm set time 
     */
    final public void setAlarmTime() {
        alarmSetoffTime = super.getStart() + alarmSetting;
    }
    /**
     * setoff time it time of start plus alarm set time 
     */
    final public void setAlarmTime(int minuts) {
        alarmSetoffTime = super.getStart() + minuts*MINUTS;
    }
    final public void snooze() {
    	super.setStartToZero();
    	setAlarmTime(DEFAULT_ALARM_SNOOZE);
		// TODO snooze in Alarm method stub Auto-generated BuYn1 февр. 2016 г.1:20:10 
	}
    /*
	 * Setter/getter block
	 */  
    final public boolean isAlarm() {
    	return getTimeLeft()<=0;
	}
    final public void setAlarmSetingMinuts(int iMinuts) {
    	alarmSetting = (iMinuts * MINUTS);
    }
    final public void setAlarmSetingSecunds(int secunds) {
    	alarmSetting = (secunds * SECONDS);
    }
    final public int getAlarmMinutsSetting() {
        return formatToMinuts(alarmSetting);
    }
	public long getAlarmTime() {
        return alarmSetoffTime;
    }

}
