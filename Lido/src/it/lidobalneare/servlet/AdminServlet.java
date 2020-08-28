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
		String jsonResponse = "{ \"type\" : \"success\" }";
		
		if (user == null) {
			response.sendRedirect("login.html");
		} else if (request.getParameter("paypal") != null) {
			DBConnect.setUserPaypal(user.getEmail(), request.getParameter("paypal"));
			out.append(jsonResponse);
		} else if (request.getParameter("chair") != null && user.getRole().equals("admin")) {
			try {
				DBConnect.addChairToLayout(request.getParameter("chair"),
										   Double.valueOf(request.getParameter("price")),
										   Integer.valueOf(request.getParameter("x")),
										   Integer.valueOf(request.getParameter("y")));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.append(jsonResponse);
		}	
		
		out.close();
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String customer = request.getParameter("customer"); 
		request.getSession().setAttribute("customer", customer); 
		return; 
	} 
}
