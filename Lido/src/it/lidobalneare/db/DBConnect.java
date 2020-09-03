package it.lidobalneare.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.mail.MessagingException;

import it.lidobalneare.Email;
import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Pass;
import it.lidobalneare.bean.Chair;
import it.lidobalneare.bean.Dish;
import it.lidobalneare.bean.Order;
import it.lidobalneare.bean.OrderQuantity;
import it.lidobalneare.bean.User;

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


	private static PreparedStatement getStatement(String query) throws SQLException {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement st = con.prepareStatement(query);
		return st;
	}

	public static User login(String email, String password) throws NullPointerException, SQLException {
		PreparedStatement s = getStatement("SELECT * FROM user WHERE email=? AND password=?");
		s.setString(1,email);
		s.setString(2,password);
		ResultSet rs = s.executeQuery();
		if (rs.next()) {
			User u = new User();
			u.setEmail(rs.getString("email"));
			u.setName(rs.getString("name"));
			u.setSurname(rs.getString("surname"));
			u.setGender(rs.getString("sex"));
			u.setActive(rs.getString("active"));
			u.setPaypal(rs.getString("paypal"));
			u.setBirthdate(rs.getDate("birthdate").toString());
			u.setRole(rs.getString("role"));
			return u;
		} else {
			throw new NullPointerException();
		}
	}

	public static void register(String email, String password, String birthdate, String gender, String name, String surname) throws SQLException, MessagingException, IllegalArgumentException {
		String code = randomAlphaNumeric(20);
		PreparedStatement st = getStatement("INSERT INTO user VALUES (?,?,?,?,?,?,null,?,'user')");
		st.setString(1,email);
		st.setString(2,password);
		st.setString(3,name);
		st.setString(4,surname);
		st.setString(5,gender);
		st.setDate(6, Date.valueOf(birthdate));
		st.setString(7, code);
		st.executeUpdate();
		Email.sendRegisterConfirmation(email, code);
	}


	public static void unlockAccount(String code) throws SQLException, NullPointerException {
		PreparedStatement st = getStatement("UPDATE user SET active = 'Y' WHERE active = ?");
		st.setString(1, code);
		int affected = st.executeUpdate();
		if(affected == 0) {
			throw new NullPointerException();
		}
	}

	// Executes a query that returns the list of every user registered, including special ones.
	public static ArrayList<User> getUserList(int from, int to) throws SQLException, NullPointerException {
		// Preparing the query.
		PreparedStatement s = getStatement("SELECT * FROM user ORDER BY email LIMIT ?,?");
		s.setInt(1, from);
		s.setInt(2, to);
		ResultSet r = s.executeQuery(); 
		ArrayList<User> list = new ArrayList<User>();

		// Generate the ArrayList.
		while (r.next()) {
			User u = new User();
			u.setEmail(r.getString("email"));
			u.setName(r.getString("name"));
			u.setSurname(r.getString("surname"));
			u.setGender(r.getString("sex"));
			u.setActive(r.getString("active"));
			u.setPaypal(r.getString("paypal"));
			u.setBirthdate(r.getDate("birthdate").toString());
			u.setRole(r.getString("role"));

			list.add(u);
		}

		return list;
	}

	// Returns the number of entries of the User table.
	public static int getUserNumber() throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("SELECT count(*) FROM user");
		ResultSet r = s.executeQuery();
		r.next();
		return r.getInt("count(*)");
	}

	//Change user's Paypal account
	public static void setUserPaypal(String email, String paypal) throws SQLException {
		PreparedStatement s = getStatement("UPDATE user SET paypal=? WHERE email=?");
		s.setString(1, paypal);
		s.setString(2, email);
		s.executeUpdate();
	}
	
	public static Pass getCustomerPass(String email, String pass_id) throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM pass WHERE pass_email = ? AND pass_id = ?");
		s.setString(1, email);
		s.setString(2, pass_id);
		ResultSet r = s.executeQuery();
		Pass p = new Pass();
		if (r.next()) {
			p.setPass_email(r.getString("pass_email"));
			p.setPass_begin(r.getDate("pass_begin"));
			p.setPass_end(r.getDate("pass_end"));
			p.setSeat(r.getString("seat"));
			p.setPass_id(r.getString("pass_id"));
			return p;
		}
		return null;
	}
	
	public static Booking getCustomerBooking(String email, String booking_id) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("SELECT * FROM booking WHERE email = ? AND booking_id = ?");
		s.setString(1, email);
		s.setString(2, booking_id);
		ResultSet r = s.executeQuery();
		Booking b = new Booking();
		if (r.next()) {
			b.setEmail(r.getString("email"));
			b.setDay(r.getDate("day"));
			b.setTime_slot(r.getInt("time_slot"));
			b.setSeat(r.getString("seat"));
			b.setBooking_id(r.getString("booking_id"));
			return b;
		}
		return null;
	}

	
	public static ArrayList<Pass> getCustomerPasses(String email) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("SELECT * FROM pass WHERE pass_email = ?");
		s.setString(1, email);
		ResultSet r = s.executeQuery();
		ArrayList<Pass> list = new ArrayList<Pass>();
		
		while (r.next()) {
			Pass p = new Pass();
			p.setPass_email(r.getString("pass_email"));
			p.setPass_begin(r.getDate("pass_begin"));
			p.setPass_end(r.getDate("pass_end"));
			p.setSeat(r.getString("seat"));
			p.setPass_id(r.getString("pass_id"));
			list.add(p);
		}
		
		return list;
	}
	
	public static ArrayList<Booking> getCustomerBookings(String email) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("SELECT * FROM booking WHERE email = ?");
		s.setString(1, email);
		ResultSet r = s.executeQuery();
		ArrayList<Booking> list = new ArrayList<Booking>();
		
		while (r.next()) {
			Booking b = new Booking();
			b.setEmail(r.getString("email"));
			b.setDay(r.getDate("day"));
			b.setTime_slot(r.getInt("time_slot"));
			b.setSeat(r.getString("seat"));
			b.setBooking_id(r.getString("booking_id"));
			list.add(b);
		}
		
		return list;
	}
	
	public static void updatePass(Pass prev, Pass next) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("UPDATE pass "
										 + "SET pass_email = ?, pass_begin = ?, pass_end = ?, seat = ? "
										 + "WHERE pass_email = ?, pass_begin = ?, pass_end = ?, seat = ?;");
		
		// SET clause.
		s.setString(1, prev.getPass_email());
		s.setDate(2, prev.getPass_begin());
		s.setDate(3, prev.getPass_end());
		s.setString(5, prev.getSeat());
		
		// WHERE clause.
		s.setString(6, next.getPass_email());
		s.setDate(7, next.getPass_begin());
		s.setDate(8, next.getPass_end());
		s.setString(10, next.getSeat());
		
		s.executeUpdate();
	}
	
	public static void updateBooking(Booking prev, Booking next) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("UPDATE booking "
				 + "SET email = ?, day = ?, time_slot = ?, seat = ? "
				 + "WHERE email = ?, day = ?, time_slot = ?, seat = ?;");

		// SET clause.
		s.setString(1, prev.getEmail());
		s.setDate(2, prev.getDay());
		s.setInt(4, prev.getTime_slot());
		s.setString(5, prev.getSeat());
		
		// WHERE clause.
		s.setString(6, next.getEmail());
		s.setDate(7, next.getDay());
		s.setInt(9, next.getTime_slot());
		s.setString(10, next.getSeat());
		
		s.executeUpdate();
	}
	
	public static ArrayList<Chair> getChairLayout() throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM chair_schema");
		ResultSet r = s.executeQuery(); 
		ArrayList<Chair> list = new ArrayList<Chair>();
		while (r.next()) {
			Chair c = new Chair();
			c.setChairname(r.getString("chairname"));
			c.setPrice(r.getDouble("price"));
			c.setX(r.getInt("pos_x"));
			c.setY(r.getInt("pos_y"));
			c.setDailyPrice(r.getDouble("dailyprice"));
			c.setPassPrice(r.getDouble("passprice"));
			c.setDetails(r.getString("details"));
			list.add(c);
		}
		return list;
	}

	public static void addChairToLayout(String chairname, double price, double dailyPrice, double passPrice, int x, int y, String note) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO chair_schema VALUES (?,?,?,?,?,?,?) ");
		s.setString(1, chairname);
		s.setInt(2, x);
		s.setInt(3, y);
		s.setDouble(4, price);
		s.setDouble(5, dailyPrice);
		s.setDouble(6, passPrice);
		s.setString(7, note);
		s.executeUpdate();
	}


	public static void moveChair(String chairname, Integer x, Integer y) throws SQLException {
		PreparedStatement s = getStatement("UPDATE chair_schema SET pos_x=?,pos_y=? WHERE chairname=?");
		s.setInt(1, x);
		s.setInt(2, y);
		s.setString(3, chairname);
		s.executeUpdate();
	}
	
	public static void updateChair(String chairname, double price, double dailyPrice, double passPrice, String note) throws SQLException {
		PreparedStatement s = getStatement("UPDATE chair_schema SET chairname=?, price=?, dailyprice=?, passprice=?, details=? WHERE chairname=?");
		s.setString(1, chairname);
		s.setDouble(2, price);
		s.setDouble(3,dailyPrice);
		s.setDouble(4, passPrice);
		s.setString(5, note);
		s.setString(6, chairname);
		s.executeUpdate();
	}

	public static Chair getChairInfo(String chair) throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM chair_schema WHERE chairname = ?");
		s.setString(1, chair);
		ResultSet r = s.executeQuery(); 
		Chair c = new Chair();
		if (r.next()) {
			c.setChairname(r.getString("chairname"));
			c.setPrice(r.getDouble("price"));
			c.setX(r.getInt("pos_x"));
			c.setY(r.getInt("pos_y"));
			c.setDailyPrice(r.getDouble("dailyprice"));
			c.setPassPrice(r.getDouble("passprice"));
			c.setDetails(r.getString("details"));
			return c;
		} else return null;
		
	}
	
	public static boolean existsPrenotation(String chairname) throws SQLException {
		PreparedStatement s = getStatement("(SELECT seat FROM booking WHERE seat = ?) UNION (SELECT seat FROM pass WHERE seat = ?)");
		s.setString(1, chairname);
		s.setString(2, chairname);
		ResultSet rs = s.executeQuery();
		if(rs.next()) return true; else return false;
	}

	public static void deleteChair(String chairname) throws SQLException {
		PreparedStatement s = getStatement("DELETE FROM chair_schema WHERE chairname=?");
		s.setString(1, chairname);
		s.executeUpdate();
	}
	
	public static boolean getChairOccupied(String chair, String date, int timeslot) {
		try {
			PreparedStatement s = null;
			if(timeslot == 0) {
				s = getStatement(""
						+ "SELECT * FROM booking b "
						+ "WHERE b.seat = ? AND b.day = ? ");
			} else {
				s = getStatement(""
						+ "SELECT * FROM booking b "
						+ "WHERE b.seat = ? AND b.day = ? AND (b.time_slot = ? OR b.time_slot = 0) ");
				s.setInt(3, timeslot);
			}
			s.setString(1, chair);
			s.setDate(2, Date.valueOf(date));
			PreparedStatement s2 = getStatement(""
					+ "SELECT * FROM pass p "
					+ "WHERE p.seat = ? AND p.pass_begin <= ? AND ? <= p.pass_end ");
			s2.setString(1, chair);
			s2.setDate(2, Date.valueOf(date));
			s2.setDate(3, Date.valueOf(date));
			ResultSet r1 = s.executeQuery();
			ResultSet r2 = s2.executeQuery();
			if(r1.next() || r2.next()) return true; else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	
	public static boolean getChairPassOccupied(String chair, String begin, int timeinterval) {
		try {
			Date beg = Date.valueOf(begin);
			Date end = new Date(beg.getTime() +  (31l*24l*60l*60l*1000l)*timeinterval);
			PreparedStatement s = getStatement(""
					+ "SELECT * FROM pass p "
					+ "WHERE p.seat = ? AND ((p.pass_begin <= ? AND ? <= p.pass_end) OR (p.pass_begin <= ? AND ? <= p.pass_end))");
			s.setString(1, chair);
			s.setDate(2, beg);
			s.setDate(3, beg);
			s.setDate(4, end);
			s.setDate(5, end);
			PreparedStatement s2 = getStatement(""
					+ "SELECT * FROM booking b "
					+ "WHERE b.seat = ? AND (? <= b.day AND b.day <= ?)");
			s2.setString(1, chair);
			s2.setDate(2, beg);
			s2.setDate(3, end);
			
			ResultSet r1 = s.executeQuery();
			ResultSet r2 = s2.executeQuery();
			if(r1.next() || r2.next()) return true; else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}


	public static void makeReservation(String email, String chair, String date, int timeslot) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO booking VALUES (?,?,?,?,?)");
		s.setString(1, email);
		s.setDate(2, Date.valueOf(date));
		s.setInt(3, timeslot);
		s.setString(4, chair);
		s.setString(5, randomAlphaNumeric(10));
		s.executeUpdate();
	}
	
	public static String makeReservation(String name, String surname, String chair, String date, int timeslot) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO booking VALUES (?,?,?,?,?)");
		s.setString(1, name + " " + surname);
		s.setDate(2, Date.valueOf(date));
		s.setInt(3, timeslot);
		s.setString(4, chair);
		String code = randomAlphaNumeric(10);
		s.setString(5, code);
		s.executeUpdate();
		return code;
	}
	
	public static void makePass(String email, String begin, int timeinterval, String chair) throws SQLException {
		Date beg = Date.valueOf(begin);
		Date end = new Date(beg.getTime() +  (31l*24l*60l*60l*1000l)*timeinterval);
		PreparedStatement s = getStatement("INSERT INTO pass VALUES (?,?,?,?,?)");
		s.setString(1, email);
		s.setDate(2, beg);
		s.setDate(3, end);
		s.setString(4, chair);
		s.setString(5, randomAlphaNumeric(10));
		s.executeUpdate();
	}
	
	// Return the list of dishes grouped in the chosen category. Used in menu.jsp and menuEditor.jsp.
	public static ArrayList<Dish> getDishesByCategory(String category) throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM menu WHERE category = ?");
		s.setString(1, category);
		ResultSet r = s.executeQuery();
		ArrayList<Dish> list = new ArrayList<Dish>();
		
		while (r.next()) {
			Dish d = new Dish();
			d.setName(r.getString("dishname"));
			d.setCategory(category);
			d.setIngredients(r.getString("ingredients"));
			d.setPrice(r.getDouble("price"));
			list.add(d);
		}
		
		return list;
	}
	
	// Method for getting the orders of a specific customer sit at a specific table. Used in menu.jsp.
	public static ArrayList<Order> getOrdersByTable (int table) throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM menuorder o, menu m, WHERE o.tableNumber = ? AND o.dish = m.dishname");
		s.setInt(1, table);
		ResultSet r = s.executeQuery();
		ArrayList<Order> list = new ArrayList<Order>();
		
		while (r.next()) {
			Order o = new Order();
			o.setId(r.getInt("o.id"));
			o.setTableNumber(table);
			o.setDate(r.getDate("o.date"));
			o.setDish(r.getString("o.dish"));
			o.setPrice(r.getDouble("m.price"));
			list.add(o);
		}
		
		return list;
	}
	
	// Gets orders and quantities.
	public static ArrayList<OrderQuantity> getOrderQuantitiesByTable (int table) throws SQLException {
		java.util.Date javatoday = Calendar.getInstance().getTime();
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date today = Date.valueOf(dateFormat.format(javatoday));
		
		PreparedStatement s = getStatement("SELECT count(*) FROM menuorder WHERE tableNumber = ? AND date = ? GROUP BY dish");
		s.setInt(1, table);
		s.setDate(2, today);
		ResultSet r = s.executeQuery();
		ArrayList<OrderQuantity> list = new ArrayList<OrderQuantity>();
		
		while (r.next()) {
			OrderQuantity o = new OrderQuantity();
			o.setDish(r.getString("dish"));
			o.setQuantity(r.getInt("count(*)"));
			list.add(o);
		}
		
		return list;
	}
	
	// Removes an order. Used in MenuServlet.
	public static void removeOrder (int id) throws SQLException {
		PreparedStatement s = getStatement("DELETE FROM menuorder WHERE id = ?");
		s.setInt(1, id);
		s.executeUpdate();
	}
	
	// Adds an order. Used in MenuServlet.
	public static void addOrder (int table, String dish) throws SQLException {
		java.util.Date javatoday = Calendar.getInstance().getTime();
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date today = Date.valueOf(dateFormat.format(javatoday));

		PreparedStatement s = getStatement("INSERT INTO menuorder VALUES (?,?,?)");
		s.setInt(1,table);
		s.setDate(2, today);
		s.setString(3, dish);
		
		s.executeUpdate();
	}
	
/*	// Sets the orders as paid.
	public static void payOrders (int table) throws SQLException {
		PreparedStatement s = getStatement("UPDATE order SET paid = 1 WHERE table = ?");
		s.setInt(1, table);
		s.executeUpdate();
	}
*/	
	// Returns a list of all the tables with any pending order.
	public static ArrayList<Integer> getTables () throws SQLException {
		ArrayList<Integer> tables = new ArrayList<Integer>();
		PreparedStatement s = getStatement("SELECT DISTINCT tableNumber FROM menuorder WHERE paid = 1");
		ResultSet r = s.executeQuery();
		
		while ( r.next() ) {
			tables.add(r.getInt("tableNumber"));
		}
		
		return tables;
	}
}