package it.lidobalneare.bean;

import java.sql.Date;

import it.lidobalneare.Utility;

public class Pass {
	private String pass_email;
	private Date pass_begin;
	private Date pass_end;
	private String seat;
	private String pass_id;
	
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
	public String getFormattedPass_begin() {
		return Utility.defaultDateFormat.format(pass_begin);
	}
	public void setPass_begin(Date pass_begin) {
		this.pass_begin = pass_begin;
	}
	public Date getPass_end() {
		return pass_end;
	}
	public String getFormattedPass_end() {
		return Utility.defaultDateFormat.format(pass_end);
	}
	public void setPass_end(Date pass_end) {
		this.pass_end = pass_end;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getPass_id() {
		return pass_id;
	}
	public void setPass_id(String pass_id) {
		this.pass_id = pass_id;
	}
	
	
}
