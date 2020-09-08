package it.lidobalneare;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import it.lidobalneare.bean.User;


public class Utility {
	
	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static int getCurrentTimeSlot() {
		LocalTime lt = LocalTime.now();
		if(lt.isBefore(LocalTime.of(12, 00))) {
			return 1;
		} else if(lt.isBefore(LocalTime.of(15, 00))) {
			return 2;
		} else {
			return 3;
		}
	}
	
	public static boolean isPass(User cntusr, HttpServletRequest request) {
		try{
			if(!cntusr.isLifeGuard() && !cntusr.isInfoMonitor()){
				return request.getParameter("prenpass").equals("YES");
			} else return false;
		} catch (NullPointerException e){
			return false;
		}
	}
	
	public static String getDate(HttpServletRequest request) {
		String date = request.getParameter("date");
		if(date==null){
			date = Utility.sqlDateFormat.format(new Date());
		}
		return date;
	}
	
	public static int getTimeSlot(User cntusr, HttpServletRequest request) {
		try {
			if(!cntusr.isLifeGuard() && !cntusr.isInfoMonitor()){
				return Integer.valueOf(request.getParameter("timeslot"));
			} else return getCurrentTimeSlot();
		} catch (NullPointerException | NumberFormatException e) {
			return getCurrentTimeSlot();
		}
	}
}
