package it.lidobalneare.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	private static String url = "jdbc:mysql://localhost:3306/lidobalneare?useSSL=false&serverTimezone=UTC";
	private static String user = "admin";
	private static String password = "1526";


	private static Statement getStatement() throws SQLException {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();
		return st;
	}

	public static int login(String email, String password) throws NullPointerException {
		try {
			String query = "SELECT * FROM customer WHERE email='"+email+"' AND password='"+password+"'";
			ResultSet rs = getStatement().executeQuery(query);
			if (rs.next()) {
				return rs.getInt(7);
			} else {
				throw new NullPointerException();
			}
		} catch (SQLException ex) {
			return -1;
		}
	}

	public static void register(String email, String password, String birthdate, String gender, String name, String surname) throws SQLException {
		System.out.println("DATE: " + birthdate);
		String query = "INSERT INTO customer VALUES ('"+email+"','"+password+"','"+name+"','"+surname+"','"+gender+"','"+birthdate+"',null,0)";
		getStatement().executeUpdate(query);
	}

}
