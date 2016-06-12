package com.stocks.timeutils;

import java.sql.Timestamp;
import java.util.Calendar;

// 
/**
 * The Class TimeUtils responsible for the time handling. It is handled
 * separately for testing reasons
 */
public class TimeUtils {

	/**
	 * Gets the time now.
	 *
	 * @return the current time
	 */
	public Timestamp getTimeNow() {
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		return new Timestamp(now);
	}

}
