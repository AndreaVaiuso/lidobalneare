package it.lidobalneare.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class MenuEditorServlet
 */
@WebServlet("/MenuEditorServlet")
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
		JSONObject data;
		int id, action;
		String dishname, ingredients;
		double price;
		try {
			data = new JSONObject(request.getParameter("data"));
			action = data.getInt("action");
			id = data.getInt("id");
			dishname = data.getString("dishname");
			ingredients = data.getString("ingredients");
			price = data.getDouble("price");
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}

		switch (action) {
		
		case 1:	// Insert a new dish.
			int category;
			try {
				category = data.getInt("category");
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			try {
				DBConnect.insertDish(dishname, category, ingredients, price);
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			break;
			
		case 2:	// Update an existing dish, except the category.
			try {
				DBConnect.updateDish(id, dishname, ingredients, price);
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			break;
			
		default:
			response.sendRedirect("errorpage.html");
		}

		response.sendRedirect("menuEditor.jsp");
	}

}
