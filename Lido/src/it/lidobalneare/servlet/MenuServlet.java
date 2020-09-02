package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class MenuServlet
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter out = response.getWriter();
		//response.setContentType("application/json");
		//String jsonResponse = "{ \"type\" : \"success\" }";
		
		switch (request.getParameter("type" )) {
		// Adds the order with the chosen id.
		case "addOrder":
			int table = Integer.parseInt(request.getParameter("table"));
			String dish = request.getParameter("dish");
			try {
				DBConnect.addOrder(table, dish);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
			
		// Removes the order with the chosen id.
		case "removeOrder" :
			try {
				DBConnect.removeOrder(Integer.parseInt(request.getParameter("id")));		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
			
		// Sets the orders paid.
		case "pay" :
			try {
				DBConnect.payOrders(Integer.parseInt(request.getParameter("table")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
			
		default :
			break;				
		}
		
		
		//out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
