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
 * Servlet implementation class TicketServlet
 */
@WebServlet("/Ticket")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		User user = (User) request.getSession().getAttribute("connecteduser");
		if(user == null) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		if(!user.isTicket()) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		try {
			switch(request.getParameter("request")) {
			case "book":
				if(request.getParameter("name").isEmpty() || request.getParameter("surname").isEmpty()) {
					throw new NullPointerException();
				}
				String code = DBConnect.makeReservation(request.getParameter("name").toUpperCase(),request.getParameter("surname").toUpperCase(),request.getParameter("chair"),request.getParameter("date"),Integer.parseInt(request.getParameter("timeslot")));
				out.append("{ \"type\" : \"success\", \"code\" : \""+code+"\" }");
				break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"fielderror\"}");	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
