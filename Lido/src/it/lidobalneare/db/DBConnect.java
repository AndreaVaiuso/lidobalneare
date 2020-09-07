package it.lidobalneare.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.mail.MessagingException;

import it.lidobalneare.Email;
import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Pass;
import it.lidobalneare.bean.Chair;
import it.lidobalneare.bean.Dish;
import it.lidobalneare.bean.Message;
import it.lidobalneare.bean.OrderQuantity;
import it.lidobalneare.bean.OrderTable;
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
		//try {
			//Context initContext = new InitialContext();
			//Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//DataSource ds = (DataSource)envContext.lookup("jdbc/lidobalneare");
			//Connection con = ds.getConnection();
			
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			Connection con = DriverManager.getConnection(url, user, password);
			PreparedStatement st = con.prepareStatement(query);
			return st;
		//} catch (NamingException e) {
		//	e.printStackTrace();
		//	return null;
		//}
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
			u.setBirthdate(rs.getDate("birthdate"));
			u.setRole(rs.getString("role"));
			return u;
		} else {
			throw new NullPointerException();
		}
	}
	
	public static User getUserName(String email) throws SQLException, NullPointerException {
		PreparedStatement st = getStatement("SELECT * FROM user WHERE email = ?");
		st.setString(1, email);
		ResultSet r = st.executeQuery();
		if(r.next()) {
			User u = new User();
			u.setEmail(r.getString("email"));
			u.setName(r.getString("name"));
			u.setSurname(r.getString("surname"));
			u.setGender(r.getString("sex"));
			u.setActive(r.getString("active"));
			u.setPaypal(r.getString("paypal"));
			u.setBirthdate(r.getDate("birthdate"));
			u.setRole(r.getString("role"));
			return u;
		} else {
			User u = new User();
			u.setEmail("Not registered user");
			StringTokenizer stok = new StringTokenizer(email);
			if(stok.hasMoreTokens()) {
				u.setName(stok.nextToken());
				while(stok.hasMoreTokens()) {
					u.setSurname(u.getSurname()+stok.nextToken());
				}
				return u;
			} else throw new NullPointerException();
			
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
			u.setBirthdate(r.getDate("birthdate"));
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
	
	
	public static ArrayList<Booking> getNotRegisteredBookings() throws SQLException{
		PreparedStatement s = getStatement("SELECT * FROM booking b WHERE NOT EXISTS(SELECT * FROM user u WHERE u.email = b.email)");
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
										 + "SET pass_begin = ?, pass_end = ?, seat = ? "
										 + "WHERE pass_email = ? AND pass_begin = ? AND pass_end = ? AND seat = ?");
		s.setDate(1, next.getPass_begin());
		s.setDate(2, next.getPass_end());
		s.setString(3, next.getSeat());
		s.setString(4, prev.getPass_email());
		s.setDate(5, prev.getPass_begin());
		s.setDate(6, prev.getPass_end());
		s.setString(7, prev.getSeat());
		
		s.executeUpdate();
	}
	
	public static void updateBooking(Booking prev, Booking next) throws SQLException, NullPointerException {
		PreparedStatement s = getStatement("UPDATE booking "
				 + "SET day = ?, time_slot = ?, seat = ? "
				 + "WHERE email = ? AND day = ? AND time_slot = ? AND seat = ?;");
		s.setDate(1, next.getDay());
		s.setInt(2, next.getTime_slot());
		s.setString(3, next.getSeat());
		s.setString(4, prev.getEmail());
		s.setDate(5, prev.getDay());
		s.setInt(6, prev.getTime_slot());
		s.setString(7, prev.getSeat());
		s.executeUpdate();
	}
	
	public static void deletePass(String email, String begin, String end, String seat) throws SQLException {
		PreparedStatement s = getStatement("DELETE FROM pass WHERE pass_email=? AND pass_begin=? AND pass_end=? AND seat=?");
		s.setString(1, email);
		s.setDate(2, Date.valueOf(begin));
		s.setDate(3, Date.valueOf(end));
		s.setString(4, seat);
		s.executeUpdate();
	}


	public static void deleteBooking(String email , String day, int timeSlot, String seat) throws SQLException {
		PreparedStatement s = getStatement("DELETE FROM booking WHERE email=? AND day=? AND time_slot=? AND seat=?");
		s.setString(1, email);
		s.setDate(2, Date.valueOf(day));
		s.setInt(3, timeSlot);
		s.setString(4, seat);
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
	
	public static String getChairOccupied(String chair, String date, int timeslot) {
		try {
			PreparedStatement s = null;
			String q1;
			if(timeslot == 0) {
				q1 = ("("
						+ "SELECT email FROM booking b "
						+ "WHERE b.seat = ? AND b.day = ? OR ? = -1) )");
			} else {
				q1 = ("("
						+ "SELECT email FROM booking b "
						+ "WHERE b.seat = ? AND b.day = ? AND (b.time_slot = ? OR b.time_slot = 0) )");
			}
			String q2 = ("("
					+ "SELECT pass_email email FROM pass p "
					+ "WHERE p.seat = ? AND p.pass_begin <= ? AND ? <= p.pass_end )");
			s = getStatement(q1 + " UNION " + q2);
			
			s.setString(1, chair);
			s.setDate(2, Date.valueOf(date));
			s.setInt(3, timeslot);
			s.setString(4, chair);
			s.setDate(5, Date.valueOf(date));
			s.setDate(6, Date.valueOf(date));
			ResultSet r = s.executeQuery();
			if(r.next()) {
				String email = r.getString("email");
				PreparedStatement s1 = getStatement("SELECT name, surname FROM user WHERE email = ?");
				s1.setString(1, email);
				ResultSet r1 = s1.executeQuery();
				// If user is registered, return name and surname, else return name surname saved in email column
				if(r1.next()) {
					return r1.getString("name") + " " + r1.getString("surname");
				} else return email;
			} else return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR - CHECK DATABASE";
		}
	}
	
	public static String getChairPassOccupied(String chair, String begin, int timeinterval) {
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
			if(r1.next() || r2.next()) return "OCCUPIED"; else return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR - CHECK DATABASE";
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
	
	// Returns the list of all dishes. Used in menu.jsp.
	public static ArrayList<Dish> getDishes () throws SQLException {
		PreparedStatement s = getStatement("SELECT * FROM menu");
		ResultSet r = s.executeQuery();
		ArrayList<Dish> list = new ArrayList<Dish>();
		
		while (r.next()) {
			Dish d = new Dish();
			d.setId(r.getInt("id"));
			d.setName(r.getString("dishname"));
			d.setCategory(r.getInt("category"));
			d.setIngredients(r.getString("ingredients"));
			d.setPrice(r.getDouble("price"));
			list.add(d);
		}
		
		return list;
	}

	// Inserts a new dish with the submitted form values. Used in menuEditor.
	public static void insertDish (String dishname, int category, String ingredients, double price) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO menu(dishname, category, ingredients, price) VALUES (?,?,?,?)");
		s.setString(1, dishname);
		s.setInt(2, category);
		s.setString(3, ingredients);
		s.setDouble(4, price);
		s.executeUpdate();
	}
	
	// Updates the chosen dish, except the category. Used in menuEditor.
	public static void updateDish (int id, String dishname, String ingredients, double price) throws SQLException {
		PreparedStatement s = getStatement("UPDATE menu SET dishname = ?, ingredients = ?, price = ? WHERE id = ?");
		s.setString(1, dishname);
		s.setString(2, ingredients);
		s.setDouble(3, price);
		s.setInt(4, id);
		s.executeUpdate();
	}

	// Adds an order. Used in MenuServlet.
	public static void insertOrder (int table, Timestamp date, int dishId) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO menuorder(tableNumber, date, dishId) VALUES (?,?,?)");
		s.setInt(1,table);
		s.setTimestamp(2, date);
		s.setInt(3, dishId);
		s.executeUpdate();
	}
	
	// Removes all paid orders of a single table. Called by OrderServlet.
	public static void completeOrders (int tableNumber) throws SQLException {
		PreparedStatement s = getStatement("DELETE FROM menuorder WHERE tableNumber = ?");
		s.setInt(1, tableNumber);
		s.executeUpdate();
	}
	
	// Returns a list of all the tables with any pending order.
	public static ArrayList<OrderTable> getTables () throws SQLException {
		ArrayList<OrderTable> tables = new ArrayList<OrderTable>();
		PreparedStatement s = getStatement("SELECT DISTINCT tableNumber, date FROM menuorder");
		ResultSet r = s.executeQuery();
		while ( r.next() ) {
			OrderTable t = new OrderTable();
			t.setTableNumber(r.getInt("tableNumber"));
			t.setDate(r.getTimestamp("date"));
			tables.add(t);
		}
		
		return tables;
	}

	// Retrieves, form the orders, the dishes and the corresponding quantities. Used in orders.jsp.
	public static ArrayList<OrderQuantity> getOrderQuantitiesByTable (int table) throws SQLException {
		PreparedStatement s = getStatement("SELECT count(*), dishname FROM menu m, menuorder o WHERE m.id = o.dishId AND o.tableNumber = ? GROUP BY m.dishname");
		s.setInt(1, table);
		ResultSet r = s.executeQuery();
		ArrayList<OrderQuantity> list = new ArrayList<OrderQuantity>();
		
		while (r.next()) {
			OrderQuantity o = new OrderQuantity();
			o.setDish(r.getString("dishname"));
			o.setQuantity(r.getInt("count(*)"));
			list.add(o);
		}
		
		return list;
	}
	
	public static void sendMessage(String title,String message,String type) throws SQLException {
		PreparedStatement s = getStatement("INSERT INTO message VALUES (now(),?,?,?)");
		s.setString(1, title);
		s.setString(2, message);
		s.setString(3, type);
		s.executeUpdate();
	}
	
	public static Message getMessage() {
		Message m = new Message();
		try {
			PreparedStatement s = getStatement("SELECT * FROM message ORDER BY date DESC");
			ResultSet r = s.executeQuery();
			if(r.next()) {
				m.setMessage(r.getString("message"));
				m.setTitle(r.getString("title"));
				m.setMessagetype(r.getString("messagetype"));
				m.setDate(r.getDate("date"));
			}
			return m;
		} catch (SQLException e) {
			return m;
		}
	}
}