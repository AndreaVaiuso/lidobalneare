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
@WebServlet("/Admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		if(request.getParameter("paypal") != null) {
			DBConnect.setUserPaypal(user.getEmail(),request.getParameter("paypal"));
			out.append(jsonResponse);
		} else if(request.getParameter("chair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.addChairToLayout(request.getParameter("chair"),Double.valueOf(request.getParameter("price")),Double.valueOf(request.getParameter("dailyprice")),Double.valueOf(request.getParameter("passprice")),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")),request.getParameter("details"));
				out.append(jsonResponse);
			} catch (NumberFormatException e) {
				out.append("{ \"type\" : \"fielderror\"}");	
			} catch (SQLIntegrityConstraintViolationException e) {
				out.append("{ \"type\" : \"duplicate\"}");		
			} catch (SQLException e) {
				out.append("{ \"type\" : \"error\"}");
			} 
		} else if(request.getParameter("movedchair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.moveChair(request.getParameter("movedchair"),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")));
			} catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \"error\"}");
			}
		} else if(request.getParameter("chairrequest") != null && user.getRole().equals("admin")) {
			try {
				Chair chair = DBConnect.getChairInfo(request.getParameter("chairrequest"));
				jsonResponse = "{ \"type\" : \"success\",  \"chairname\" : \""+chair.getChairname()+"\",  \"price\" : \""+chair.getPrice()+"\""
						+ ",\"dailyprice\" : \""+chair.getDailyPrice()+"\", \"passprice\" : \""+chair.getPassPrice()+"\", \"details\" : \""+chair.getDetails()+"\"}";
				out.append(jsonResponse);
			}  catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \"error\"}");
			}
		} else if(request.getParameter("updatechair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.updateChair(request.getParameter("updatechair"), Double.valueOf(request.getParameter("price")), Double.valueOf(request.getParameter("dailyprice")), Double.valueOf(request.getParameter("passprice")), request.getParameter("details"));
				out.append(jsonResponse);
			} catch (NumberFormatException e) {
				out.append("{ \"type\" : \"fielderror\"}");	
			} catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \"error\"}");
			}
		} else if(request.getParameter("deletechair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.deleteChair(request.getParameter("deletechair"));
				out.append(jsonResponse);
			} catch (SQLException e) {
				e.printStackTrace();
				out.append("{ \"type\" : \"error\"}");
			}
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customer = request.getParameter("customer"); 
		request.getSession().setAttribute("customer", customer); 
		return; 

	}

}
