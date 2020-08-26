package it.lidobalneare.bean;

import java.sql.Date;

public class Pass {
	private String pass_email;
	private Date pass_begin;
	private Date pass_end;
	private int pass_people_num;
	private String seat;
	
	// Setters and Getters
	public String getPass_email() {
		return pass_email;
	}
	public void setPass_email(String pass_email) {
		this.pass_email = pass_email;
	}
	public Date getPass_begin() {
		return pass_begin;
	}
	public void setPass_begin(Date pass_begin) {
		this.pass_begin = pass_begin;
	}
	public Date getPass_end() {
		return pass_end;
	}
	public void setPass_end(Date pass_end) {
		this.pass_end = pass_end;
	}
	public int getPass_people_num() {
		return pass_people_num;
	}
	public void setPass_people_num(int pass_people_num) {
		this.pass_people_num = pass_people_num;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
	
}
