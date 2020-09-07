package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class MenuServlet
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tableNumber = Integer.valueOf(request.getParameter("table_number"));
		double total;
		
		try {
			total = DBConnect.getTotalOfOrders(tableNumber);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("PayPal payment - price: ");
		System.out.println(total);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray pendingOrders;
		int tableNumber = Integer.valueOf(request.getParameter("table_number"));

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		try {
			pendingOrders = new JSONArray(request.getParameter("pendingOrders"));
		} catch (JSONException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"error\" }");
			out.close();
			return; 
		}
		
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());

		for (int i = 0; i < pendingOrders.length(); i++) {
			try {
				DBConnect.insertOrder(tableNumber, ts, pendingOrders.getJSONObject(i).getInt("dishId"));
			} catch (Exception e) {

				e.printStackTrace();
				out.append("{ \"type\" : \"error\" }");
				out.close();

				return; 
			}
		}

		out.append("{ \"type\" : \"success\" }");
		out.close();


	}
}
