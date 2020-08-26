package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Pass;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class BookingAdmin
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
