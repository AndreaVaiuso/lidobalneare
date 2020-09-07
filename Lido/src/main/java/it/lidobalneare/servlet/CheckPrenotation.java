package it.lidobalneare.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.lidobalneare.bean.Booking;
import it.lidobalneare.bean.Pass;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class CheckPrenotation
 */
@WebServlet("/CheckPrenotation")
public class CheckPrenotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckPrenotation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String code = request.getParameter("code");
		if(type == null || email == null || code == null) {
			response.sendRedirect("errorpage.html");
			return;
		}
		try {
			switch(type) {
			case "pass":
				Pass p = DBConnect.getCustomerPass(email, code);
				session.setAttribute("pass", p);
				break;
			case "booking":
				Booking b = DBConnect.getCustomerBooking(email, code);
				session.setAttribute("booking", b);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("booking", null);
			session.setAttribute("pass", null);
		} finally {
			response.sendRedirect("checkpage.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
