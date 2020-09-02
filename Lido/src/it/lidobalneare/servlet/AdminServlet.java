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

import it.lidobalneare.bean.Chair;
import it.lidobalneare.bean.User;
import it.lidobalneare.db.DBConnect;

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
		if(!user.isAdmin()) {
			response.sendRedirect("login.html");
			out.close();
			return;
		}
		try {
			switch(request.getParameter("request")) {
			case "updatechair":
				DBConnect.updateChair(request.getParameter("updatechair"), Double.valueOf(request.getParameter("price")), Double.valueOf(request.getParameter("dailyprice")), Double.valueOf(request.getParameter("passprice")), request.getParameter("details"));
				out.append(jsonResponse);
				break;
			case "addchair":
				DBConnect.addChairToLayout(request.getParameter("chair"),Double.valueOf(request.getParameter("price")),Double.valueOf(request.getParameter("dailyprice")),Double.valueOf(request.getParameter("passprice")),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")),request.getParameter("details"));
				out.append(jsonResponse);
				break;
			case "movechair":
				DBConnect.moveChair(request.getParameter("chair"),Integer.valueOf(request.getParameter("x")),Integer.valueOf(request.getParameter("y")));
				break;
			case "chairinfo":
				Chair chair = DBConnect.getChairInfo(request.getParameter("chair"));
				jsonResponse = "{ \"type\" : \"success\",  \"chairname\" : \""+chair.getChairname()+"\",  \"price\" : \""+chair.getPrice()+"\""
						+ ",\"dailyprice\" : \""+chair.getDailyPrice()+"\", \"passprice\" : \""+chair.getPassPrice()+"\", \"details\" : \""+chair.getDetails()+"\"}";
				out.append(jsonResponse);
				break;
			case "deletechair":
				System.out.println("DELETE");
				boolean exists = DBConnect.existsPrenotation(request.getParameter("chair"));
				
				if(exists) {
					out.append("{ \"type\" : \"exists\"}");
				} else {
					DBConnect.deleteChair(request.getParameter("chair"));
					out.append(jsonResponse);
				}
				break;
			case "existsprenotation":
				if(DBConnect.existsPrenotation(request.getParameter("chair"))) {
					out.append("{ \"type\" : \"exists\"}");
				} else {
					out.append("{ \"type\" : \"notexists\"}");
				}
				break;
			}
		} catch (NumberFormatException e) {
			out.append("{ \"type\" : \"fielderror\"}");	
		} catch (SQLIntegrityConstraintViolationException e) {
			out.append("{ \"type\" : \"duplicate\"}");		
		} catch (SQLException e) {
			out.append("{ \"type\" : \"error\"}");
		} 
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customer = request.getParameter("customer"); 
		request.getSession().setAttribute("customer", customer); 
		return; 
	}
}
