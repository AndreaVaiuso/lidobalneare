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
		switch ( request.getParameter("type") ) {
		// Adds the order with the chosen id.
	/*	case "addOrder":
			int table = Integer.parseInt(request.getParameter("table"));
			String dish = request.getParameter("dish");
			try {
				DBConnect.addOrder(table, dish);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
	*/		
		// Removes the order with the chosen id.
	/*	case "removeOrder" :
			try {
				DBConnect.removeOrder(Integer.parseInt(request.getParameter("id")));		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
	*/		
		// Sets the orders paid.
	/*	case "pay" :
			try {
				DBConnect.payOrders(Integer.parseInt(request.getParameter("table")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("menu.jsp");
			break;
	*/		
		// Table's QR-code scanned.
		case "tableqr" :
			Order o = new Order();
			o.setTableNumber(Integer.parseInt(request.getParameter("num")));
			request.getSession().setAttribute("orderTable", o);
			response.sendRedirect("menu.jsp");
			break;
			
		default :
			response.sendRedirect("errorpage.html");
			break;				
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Used by menu.js to confirm the order.
	 * Inserts into DB every single order.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray pendingOrder;
		Order o = (Order) request.getSession().getAttribute("orderTable");
		int tableNum = o.getTableNumber();
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		try {
			pendingOrder = new JSONArray(request.getParameter("pendingOrder"));
		} catch (JSONException e) {
			e.printStackTrace();
			out.append("{ \"type\" : \"error\" }");
			out.close();
			return; 
		}
		
		// PAGAMENTO PAYPAL.
		
		for (int i = 0; i < pendingOrder.length(); i++) {
			try {
				DBConnect.addOrder(tableNum, pendingOrder.getJSONObject(i).getString("dish"));
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
