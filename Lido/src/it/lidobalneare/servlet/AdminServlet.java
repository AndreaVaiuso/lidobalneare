package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.lidobalneare.bean.Chair;
import it.lidobalneare.bean.User;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class SetPaypalAccount
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("connecteduser");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		User user = (User) session.getAttribute("connecteduser");
		if(user == null) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		if(request.getParameter("paypal") != null) {
			DBConnect.setUserPaypal(user.getEmail(),request.getParameter("paypal"));
			out.append("{ \"type\" : \" success \"}");
		} else if(request.getParameter("chair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.addChairToLayout(request.getParameter("chair"),Double.valueOf(request.getParameter("price")),Double.valueOf(request.getParameter("dailyprice")),Double.valueOf(request.getParameter("passprice")),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")),request.getParameter("details"));
				out.append("{ \"type\" : \" success \"}");
			} catch (NumberFormatException e) {
				out.append("{ \"type\" : \" fielderror \"}");	
			} catch (SQLIntegrityConstraintViolationException e) {
				out.append("{ \"type\" : \" duplicate \"}");		
			} catch (SQLException e) {
				out.append("{ \"type\" : \" error \"}");
			} 
		} else if(request.getParameter("movedchair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.moveChair(request.getParameter("movedchair"),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")));
			} catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \" error \"}");
			}
		} else if(request.getParameter("chairrequest") != null && user.getRole().equals("admin")) {
			try {
				Chair chair = DBConnect.getChairInfo(request.getParameter("chairrequest"));
				out.append("{ \"type\" : \" success \",  \"chairname\" : \" "+chair.getChairname()+" \",  \"price\" : \" "+chair.getPrice()+" \""
						+ ", \"dailyprice\" : \" "+chair.getDailyPrice()+" \", \"passprice\" : \" "+chair.getPassPrice()+" \", \"details\" : \" "+chair.getDetails()+" \"}");
			}  catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \" error \"}");
			}
		} else if(request.getParameter("updatechair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.updateChair(request.getParameter("updatechair"), Double.valueOf(request.getParameter("price")), Double.valueOf(request.getParameter("dailyprice")), Double.valueOf(request.getParameter("passprice")), request.getParameter("details"));
				out.append("{ \"type\" : \" success \"}");
			} catch (NumberFormatException e) {
				out.append("{ \"type\" : \" fielderror \"}");	
			} catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \" error \"}");
			}
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve selected user in admin panel. 
		String customer = request.getParameter("customer"); 

		//	ArrayList<Pass> passes = new ArrayList<Pass>(); 
		//	ArrayList<Booking> bookings = new ArrayList<Booking>(); 

		HttpSession session = null; 

		PrintWriter out = response.getWriter(); 
		response.setContentType("text/plain"); 
		/* 
				// DB Queries. 
				try { 
					passes = DBConnect.getCustomerPasses(customer); 
					bookings = DBConnect.getCustomerBookings(customer); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
					String jsonResponse = "{ \"type\" : \"error\" }"; 
					out.append(jsonResponse); 
					out.close(); 
					return; 
				} 
		 */ 
		/* 
				response.setContentType("text/xml"); 
				PrintWriter out = response.getWriter(); 
				out.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" 
						+ "<reservations>" 
						+ "<passes>"); 

				for (int i = 0; i < passes.size(); i++) { 
					out.append("<pass>"); 
					out.append("<pass_email>" + passes.get(i).getPass_email() + "</pass_email>"); 
					out.append("<pass_begin>" + passes.get(i).getPass_begin() + "</pass_begin>"); 
					out.append("<pass_end>" + passes.get(i).getPass_end() + "</pass_end>"); 
					out.append("<pass_people_num>" + passes.get(i).getPass_people_num() + "</pass_people_num>"); 
					out.append("<pass_seat>" + passes.get(i).getSeat() + "</pass_seat>"); 
					out.append("</pass>"); 
				} 

				out.append("</passes>" 
						+ "<bookings>"); 

				for (int i = 0; i < bookings.size(); i++) { 
					out.append("<booking>"); 
					out.append("<book_email>" + bookings.get(i).getEmail() + "</book_email>"); 
					out.append("<book_day>" + bookings.get(i).getDay() + "</book_day>"); 
					out.append("<book_time_slot>" + bookings.get(i).getTime_slot() + "</book_time_slot>"); 
					out.append("<book_seat>" + bookings.get(i).getSeat() + "</book_seat>"); 
					out.append("</booking>"); 
				} 

				out.append("</bookings>" 
						+ "</reservations>"); 

				out.close(); 
				return; 
		 */ 
		session = request.getSession(); 

		session.setAttribute("customer", customer); 
		//	session.setAttribute("passes", passes); 
		//	session.setAttribute("bookings", bookings); 

		String jsonResponse = "{ \"type\" : \"success\" }"; 
		out.append(jsonResponse); 
		out.close(); 
		return; 
	}

}
