package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lidobalneare.bean.User;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("connecteduser");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String jsonResponse = "{ \"type\" : \"success\" }";
		if(user == null) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		if(!user.isCook()) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		response.setContentType("application/json");
		try {
			int tableNumber = Integer.valueOf(request.getParameter("table"));
			DBConnect.completeOrders(tableNumber);
			out.append(jsonResponse);
		} catch (SQLException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"error\" }");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"error\" }");
		}
		
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
