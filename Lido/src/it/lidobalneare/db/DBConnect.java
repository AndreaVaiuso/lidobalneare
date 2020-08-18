package it.lidobalneare.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.mail.MessagingException;

import it.lidobalneare.Email;

public class DBConnect {
	private static String url = "jdbc:mysql://localhost:3306/lidobalneare?useSSL=false&serverTimezone=UTC";
	private static String user = "admin";
	private static String password = "1526";


	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}


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

	public static void register(String email, String password, String birthdate, String gender, String name, String surname) throws SQLException, MessagingException {
		String code = randomAlphaNumeric(20);
		String query = "INSERT INTO customer VALUES ('"+email+"','"+password+"','"+name+"','"+surname+"','"+gender+"','"+birthdate+"',null,'"+code+"')";	
		getStatement().executeUpdate(query);
		Email.sendRegisterConfirmation(email, code);
	}


	public static void unlockAccount(String code) throws SQLException {
		String query = "UPDATE customer SET active = 'Y' WHERE active = '"+code+"'";
		getStatement().executeUpdate(query);
		
	}

}



