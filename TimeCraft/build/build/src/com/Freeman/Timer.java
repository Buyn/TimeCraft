package com.Freeman;

import java.text.SimpleDateFormat;

/**
 * Created by BuYn on 12.12.2014.
 */
public class Timer {
    //Constants
    public static final int SECONDS = 1000;
    public static final int MINUTS = SECONDS * 60;
    public static final int HOURS = MINUTS * 60;
    public static final int UA = HOURS * 2;
    public static final int DEFAULT_ALARM = 9;
    //INICEALISATION
    private long    lStart = 0;
    private long    lSet   = 0;
    private long    lPause = 0;
    private int     iAlarmSet = 0;
    private SimpleDateFormat sdf;
//    End of Intedger Block
// Bagen of Constructors block
    public Timer(){
        iAlarmSet = DEFAULT_ALARM;
        sdf = new SimpleDateFormat("HH:mm:ss");
        lStart = System.currentTimeMillis();
        long lMinuts =   MINUTS *9;
        lSet = lStart + (DEFAULT_ALARM * MINUTS);
    }
    public Timer(int iMinuts) {
        iAlarmSet = iMinuts;
        lStart = System.currentTimeMillis();
        lSet = lStart + (iMinuts * MINUTS);
        sdf = new SimpleDateFormat("HH:mm:ss");
    }
    //    end of Constructors block
//    Bagen of Metods block
//    Private Metods
    private String formatLong(long lConvert){
        return sdf.format(lConvert - UA);
    }
    private long getNow(){
        return System.currentTimeMillis();
    }
//  Public  Metods
    public String getFormatTimeLeft(){
        return formatLong(getTimeLeft());
    }
    public long getTimeLeft(){
        return lSet - getNow();
    }
    public long getSekundsLeft(){
        return (lSet - getNow())/SECONDS;
    }
    public String getFormatDeltaTime(){
        return formatLong(getDeltaTime());}
    public long getDeltaTime() {
        return getNow() - lStart + lPause;
    }
    public String getFormatedTime(){
        return formatLong(getNow());
    }
    public void setPause(){
        lPause = getNow() - lStart + lPause;
        lStart = getNow();
    }
    public void unPause(){
        lStart = getNow();
    }
//    End of Metods block
//  seters and geters
    public void setToZeroStart(){
        lStart = getNow();
        lPause = 0;
    }
    public void setAlarm() {
        lSet = getNow() + (iAlarmSet * MINUTS);
    }
    public void setAlarm(int iMinuts) {
//        iAlarmSet = iMinuts;
        lSet = getNow() + (iMinuts * MINUTS);
    }
    public void setStart(long dStart) {
        this.lStart = dStart;
    }
    public void setPause(long lPause) {
        this.lPause = lPause;
    }
    public void setAlarmSet(int iAlarmSet) {
        this.iAlarmSet = iAlarmSet;
    }
    public int getAlarmSet() {
        return iAlarmSet;
    }
    public long getPause() {
        return lPause;
    }
    public long getAlarm() {
        return lSet;
    }
    public long getStart() {
        return lStart;
    }

}
