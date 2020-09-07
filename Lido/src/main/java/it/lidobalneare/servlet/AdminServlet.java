package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Chair;
import it.lidobalneare.bean.Pass;
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
			case "customer":
				String customer = request.getParameter("selecteduser"); 
				request.getSession().setAttribute("customer", customer); 
				out.append(jsonResponse);
				break;
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
			case "message":
				DBConnect.sendMessage(request.getParameter("title"),request.getParameter("message"),request.getParameter("messagetype"));
				out.append(jsonResponse);
				break;
			default:
				response.sendRedirect("errorpage.html");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String jsonResponse = "{ \"type\" : \"success\" }";
		try {
			switch(request.getParameter("request")) {
			case "update":
				if(request.getParameter("type").equals("pass")) {
					Pass oldpass = new Pass();
					Pass newpass = new Pass();
					oldpass.setPass_email(request.getParameter("pass_email"));
					oldpass.setPass_begin(Date.valueOf(request.getParameter("pass_begin")));
					oldpass.setPass_end(Date.valueOf(request.getParameter("pass_end")));
					oldpass.setSeat(request.getParameter("seat"));
					newpass.setPass_email(request.getParameter("pass_email"));
					newpass.setPass_begin(Date.valueOf(request.getParameter("new_pass_begin")));
					newpass.setPass_end(Date.valueOf(request.getParameter("new_pass_end")));
					newpass.setSeat(request.getParameter("new_seat"));
					DBConnect.updatePass(oldpass, newpass);
					out.append(jsonResponse);
				} else if(request.getParameter("type").equals("booking")) {
					Booking oldbooking = new Booking();
					Booking newbooking = new Booking();
					oldbooking.setEmail(request.getParameter("email"));
					oldbooking.setDay(Date.valueOf(request.getParameter("day")));
					oldbooking.setTime_slot(Integer.valueOf(request.getParameter("time_slot")));
					oldbooking.setSeat(request.getParameter("seat"));
					newbooking.setEmail(request.getParameter("email"));
					newbooking.setDay(Date.valueOf(request.getParameter("new_day")));
					newbooking.setTime_slot(Integer.valueOf(request.getParameter("new_time_slot")));
					newbooking.setSeat(request.getParameter("new_seat"));
					DBConnect.updateBooking(oldbooking, newbooking);
					out.append(jsonResponse);
				}
				break;
			case "delete":
				if(request.getParameter("type").equals("pass")) {
					DBConnect.deletePass(request.getParameter("pass_email"),request.getParameter("pass_begin"),request.getParameter("pass_end"),request.getParameter("seat"));
					out.append(jsonResponse);
				} else if(request.getParameter("type").equals("booking")) {
					DBConnect.deleteBooking(request.getParameter("email"),request.getParameter("day"),Integer.valueOf(request.getParameter("time_slot")),request.getParameter("seat"));
					out.append(jsonResponse);
				}
				break;
			default:
				response.sendRedirect("errorpage.html");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.close();
	}
}
