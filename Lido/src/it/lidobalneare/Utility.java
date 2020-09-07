package it.lidobalneare;

import java.text.SimpleDateFormat;
import java.time.LocalTime;


public class Utility {
	
	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static int getCurrentTimeSlot() {
		LocalTime lt = LocalTime.now();
		if(lt.isBefore(LocalTime.of(12, 00))) {
			return 1;
		} else if(lt.isBefore(LocalTime.of(15, 00))) {
			return 2;
		} else {
			return 3;
		}
	}
}
