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

import it.lidobalneare.bean.User;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/Customer")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String jsonResponse = "{ \"type\" : \"success\" }";
		User user = (User) request.getSession().getAttribute("connecteduser");
		if(user == null) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		if(!user.isCustomer()) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		try {
			switch(request.getParameter("request")) {
			case "paypalchange":
				DBConnect.setUserPaypal(user.getEmail(),request.getParameter("paypal"));
				out.append(jsonResponse);
				break;
			case "book":
				DBConnect.makeReservation(user.getEmail(),request.getParameter("chair"),request.getParameter("date"),Integer.parseInt(request.getParameter("timeslot")));
				out.append(jsonResponse);
				break;
			case "pass":
				DBConnect.makePass(user.getEmail(), request.getParameter("start"), Integer.parseInt(request.getParameter("duration")), request.getParameter("chair"));
				out.append(jsonResponse);
				break;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"fielderror\"}");	
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"duplicate\"}");		
		} catch (SQLException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"error\"}");
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
