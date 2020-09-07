package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.lidobalneare.SHA256;
import it.lidobalneare.db.DBConnect;
import it.lidobalneare.bean.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet", "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve login data.
    	String email = request.getParameter("email");
		String password = request.getParameter("password");
		password = SHA256.encode(password);
		
		// Create response.
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String jsonResponse = "";
		HttpSession session = null;
		try {
			User user = DBConnect.login(email, password);
			
			if (!user.getActive().equals("Y")) {	//Not active account
				jsonResponse = "{ \"type\" : \"notActive\" }";
			} else {	// Login successful.
				session = request.getSession();
				session.setAttribute("connecteduser", user);
				jsonResponse = "{ \"type\" : \"loginSuccess\" , \"role\" : \"" + user.getRole() + "\"}";
			}
		} catch (NullPointerException e) {	// Login failure or wrong password.
			jsonResponse = "{ \"type\" : \"loginError\" }";
		} catch (SQLException e1) {	// DB error.
			e1.printStackTrace();
			return;
		}
		
		out.append(jsonResponse);
		out.close();
		return;
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.sendRedirect("login.html");
    }
}
