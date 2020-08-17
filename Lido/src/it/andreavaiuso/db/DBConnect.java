package it.andreavaiuso.db;

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
		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();
		return st;
	}
	
	public static void login(String email, String password) throws NullPointerException {
		try {
			String query = "SELECT * FROM customer WHERE email='"+email+"' AND password='"+password+"'";
			ResultSet rs = getStatement().executeQuery(query);

			if (rs.next()) {
				System.out.println(rs.getString(1));
			} else {
				throw new NullPointerException();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
