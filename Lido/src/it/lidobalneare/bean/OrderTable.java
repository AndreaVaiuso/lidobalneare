package it.lidobalneare.bean;

import java.sql.Date;

public class OrderTable {
	private int tableNumber;
	private Date date;
	
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
