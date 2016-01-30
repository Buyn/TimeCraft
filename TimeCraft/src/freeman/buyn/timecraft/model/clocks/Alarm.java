package freeman.buyn.timecraft.model.clocks;

/**
 * extend timer fuction for implement
 * seting alarm
 * 
 */
public class Alarm extends Timer {
    private long    lAlarmSetMillSec   = 0;
    public static final int DEFAULT_ALARM_SET = 9;
	/*
	 * Initialization Methods Block
	 */
    /**
     * Null constructir use defalt setingd from DEFAULT_ALARM_SET
     */
    public Alarm(){
    	this(DEFAULT_ALARM_SET);
    }   
    /**
     * Constructor set parametr minut to set alarm
     * @param iMinuts minuts to set alarm
     */
    public Alarm(int iMinuts) {
    	super();
        lAlarmSetMillSec = super.getStart() + (iMinuts * MINUTS);
    }
	/*
	 * Private Methods Block
	 */
    private int formatToMinuts(long timeInMillSec) {
		return (int) ((timeInMillSec/SECUNDS)/MINUTS);
    }
	/*
	 * Public Methods Block
	 */
    final public long getTimeLeft(){
        return lAlarmSetMillSec - super.getNow();
    }
    final public long getSekundsLeft(){
        return (lAlarmSetMillSec - super.getNow())/SECUNDS;
    }
    final public String getFormatTimeLeft(){
        return formatLong(getTimeLeft());
    }
    final public boolean isAlarm() {
    	return getTimeLeft()<=0;
	}
    final public void setAlarm() {
        lAlarmSetMillSec = getNow() + lAlarmSetMillSec;
    }
    /*
	 * Setter/getter block
	 */

    final public void setAlarmMinuts(int iMinuts) {
        lAlarmSetMillSec = getNow() + (iMinuts * MINUTS);
    }
    final public void setAlarmSecunds(int secunds) {
        lAlarmSetMillSec = getNow() + (secunds * SECUNDS);
    }
    final public int getAlarmMinuts() {
        return formatToMinuts(lAlarmSetMillSec);
    }
		public long getAlarm() {
        return lAlarmSetMillSec;
    }
}
