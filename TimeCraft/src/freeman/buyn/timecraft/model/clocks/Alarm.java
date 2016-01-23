package freeman.buyn.timecraft.model.clocks;

import java.text.SimpleDateFormat;

public class Alarm extends Timer {
    private long    lSet   = 0;
    private int     iAlarmSet = 0;
	/*
	 * Initialization Methods Block
	 */
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
	/*
	 * Private Methods Block
	 */

	/*
	 * Public Methods Block
	 */

	/*
	 * Setter/getter block
	 */

}
