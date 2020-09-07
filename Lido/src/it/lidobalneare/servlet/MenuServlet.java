package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.lidobalneare.bean.Order;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class MenuServlet
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		Date date = new Date(Calendar.getInstance().getTime().getTime());

		for (int i = 0; i < pendingOrders.length(); i++) {
			try {
				DBConnect.insertOrder(tableNumber, date, pendingOrders.getJSONObject(i).getInt("dishId"));
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
