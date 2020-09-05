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
 * Servlet implementation class MenuEditorServlet
 */
@WebServlet({"/MenuEditorServlet","/Menu"})
public class MenuEditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuEditorServlet() {
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
		int action = Integer.valueOf(request.getParameter("action"));
		String dishname = request.getParameter("dishname"),
			   ingredients = request.getParameter("ingredients");
		double price = Double.valueOf(request.getParameter("price"));
		
		switch (action) {
		
		case 1:	// Insert a new dish. Id not needed.
			int category = Integer.valueOf(request.getParameter("category"));
			try {
				DBConnect.insertDish(dishname, category, ingredients, price);
			} catch (SQLException e) {
				System.out.println(e);
				return;
			}
			break;
			
		case 2:	// Update an existing dish. Category not needed.
			int id = Integer.valueOf(request.getParameter("id"));
			try {
				DBConnect.updateDish(id, dishname, ingredients, price);
			} catch (SQLException e) {
				System.out.println(e);
				return;
			}
			break;
			
		default:
			response.sendRedirect("errorpage.html");
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.append("{ \"type\" : \"success\"}");
		out.close();
	}

}
