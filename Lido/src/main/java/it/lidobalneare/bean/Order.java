package it.lidobalneare.bean;

import java.sql.Timestamp;

import it.lidobalneare.Utility;

public class Order {
	private int id;
	private int tableNumber;
	private Timestamp date;
	private int dishId;
	private double price;	// Not present in DB. Must retrieve from table menu.
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getFormattetDate() {
		return Utility.defaultDateFormat.format(date);
	}
	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
