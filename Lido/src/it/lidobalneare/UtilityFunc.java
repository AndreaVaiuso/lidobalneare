package it.lidobalneare;

import java.time.LocalTime;

public class UtilityFunc {
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
