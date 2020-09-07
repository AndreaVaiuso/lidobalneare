package it.lidobalneare.bean;

public class Chair {
	
	private String chairname;
	private double price;
	private double passPrice;
	private double dailyPrice;
	private int x;
	private int y;
	private String details;
	
	public String getChairname() {
		return chairname;
	}
	public void setChairname(String chairname) {
		this.chairname = chairname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getPassPrice() {
		return passPrice;
	}
	public void setPassPrice(double passPrice) {
		this.passPrice = passPrice;
	}
	public double getDailyPrice() {
		return dailyPrice;
	}
	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
