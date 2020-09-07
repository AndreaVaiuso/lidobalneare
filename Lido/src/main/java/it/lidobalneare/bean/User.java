package it.lidobalneare.bean;

import java.sql.Date;

import it.lidobalneare.Utility;

public class User {
	private String email = "";
	private String name = "";
	private String surname = "";
	private String gender = "";
	private Date birthdate;
	private String paypal = "";
	private String active = "";
	private String role = "";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}
	
	public String getFormattedBirthDate() {
		return Utility.defaultDateFormat.format(birthdate);
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPaypal() {
		return paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isAdmin() {
		if(getRole().equals("admin")) return true; else return false;
	}
	public boolean isCustomer() {
		if(getRole().equals("customer")) return true; else return false;
	}
	public boolean isCook() {
		if(getRole().equals("cook")) return true; else return false;
	}
	public boolean isTicket() {
		if(getRole().equals("ticket")) return true; else return false;
	}
	public boolean isInfoMonitor() {
		if(getRole().equals("info")) return true; else return false;
	}
	public boolean isLifeGuard() {
		if(getRole().equals("lifeguard")) return true; else return false;
	}
}
