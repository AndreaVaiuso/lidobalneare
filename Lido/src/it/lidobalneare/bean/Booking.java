package it.lidobalneare.bean;

import java.sql.Date;

public class Booking {
	private String email;
	private Date day;
	private int time_slot;
	private String seat;
	private String booking_id;
	
	// Setters and Getters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public int getTime_slot() {
		return time_slot;
	}
	public void setTime_slot(int time_slot) {
		this.time_slot = time_slot;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(String booking_id) {
		this.booking_id = booking_id;
	}
}
