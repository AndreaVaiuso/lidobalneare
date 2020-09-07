package it.lidobalneare.bean;

import java.sql.Timestamp;

public class OrderTable {
	private int tableNumber;
	private Timestamp date;
	
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
}
