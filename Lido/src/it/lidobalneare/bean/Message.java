package it.lidobalneare.bean;

import java.sql.Date;

public class Message {
	private String title = "No message";
	private String message = "No message to display";
	private String messagetype = "STANDARD";
	private Date date = new Date(System.currentTimeMillis());
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
	
}
