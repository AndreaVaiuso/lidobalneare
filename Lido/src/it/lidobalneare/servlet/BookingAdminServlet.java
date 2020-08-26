package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;  

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Pass;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class BookingAdminServlet
 */
@WebServlet("/BookingAdminServlet")
public class BookingAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingAdminServlet() {
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
		String jsonString = request.getParameter("jsonData");
        JSONArray objs = null;
        JSONObject p = null, 
        		   n = null;
        
        PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        
        try {
        	objs = new JSONArray(jsonString);
        	p = objs.getJSONObject(0);
        	n = objs.getJSONObject(1);
        } catch (JSONException e) {
        	e.printStackTrace();
        	String jsonResponse = "{ \"type\" : \"error\" }";
    		out.append(jsonResponse);
    		out.close();
    		return;
        }
		
		if ( !p.isNull("pass_email") ) { // It's a Pass object.
			Pass prev = new Pass();
			Pass next = new Pass();
			
			try {
				prev.setPass_email(p.getString("pass_email"));
				prev.setPass_begin(Date.valueOf(p.getString("pass_begin")));
				prev.setPass_end(Date.valueOf(p.getString("pass_end")));
				prev.setPass_people_num(p.getInt("pass_people_num"));
				prev.setSeat(p.getString("seat"));
				
				next.setPass_email(n.getString("pass_email"));
				next.setPass_begin(Date.valueOf(n.getString("pass_begin")));
				next.setPass_end(Date.valueOf(n.getString("pass_end")));
				next.setPass_people_num(n.getInt("pass_people_num"));
				next.setSeat(n.getString("seat"));
			} catch (JSONException e) {
	        	e.printStackTrace();
	        	String jsonResponse = "{ \"type\" : \"error\" }";
	    		out.append(jsonResponse);
	    		out.close();
	    		return;
	        }
			
			try {
				DBConnect.updatePass(prev, next);
			} catch (SQLException e) {
	        	e.printStackTrace();
	        	String jsonResponse = "{ \"type\" : \"error\" }";
	    		out.append(jsonResponse);
	    		out.close();
	    		return;
	        }
		} else if ( !p.isNull("email") ) {	// It's a Booking object.
			Booking prev = new Booking();
			Booking next = new Booking();
			
			try {
				prev.setEmail(p.getString("email"));
				prev.setDay(Date.valueOf(p.getString("day")));
				prev.setTime_slot(p.getInt("time_slot"));
				prev.setSeat(p.getString("seat"));
				
				next.setEmail(n.getString("email"));
				next.setDay(Date.valueOf(n.getString("day")));
				next.setTime_slot(n.getInt("time_slot"));
				next.setSeat(n.getString("seat"));
			} catch (JSONException e) {
	        	e.printStackTrace();
	        	String jsonResponse = "{ \"type\" : \"error\" }";
	    		out.append(jsonResponse);
	    		out.close();
	    		return;
	        }
			
			try {
				DBConnect.updateBooking(prev, next);
			} catch (SQLException e) {
	        	e.printStackTrace();
	        	String jsonResponse = "{ \"type\" : \"error\" }";
	    		out.append(jsonResponse);
	    		out.close();
	    		return;
	        }
			
			String jsonResponse = "{ \"type\" : \"success\" }";
    		out.append(jsonResponse);
    		out.close();
    		return;
		}
	}

}
