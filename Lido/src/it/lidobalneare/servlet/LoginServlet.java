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
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve login data.
    	String email = request.getParameter("email");
		String password = request.getParameter("password");
		password = SHA256.encode(password);
		try {
			User user = DBConnect.login(email, password);
			if (!user.getActive().equals("Y")) {	//Not active account
				String jsonObject = "{ \"type\" : \"notActive\" }";
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.append(jsonObject);
				out.close();
				return;
			} else {	// Login successful.
				HttpSession session = request.getSession();
				
				
			}
		} catch (NullPointerException e) {
			// Send to client login failure or password wrong.
			String jsonObject = "{ \"type\" : \"loginError\" }";
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.append(jsonObject);
			out.close();
			return;
		} catch (SQLException e1) {
			// DB error.
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("Hello");
		
	}

}
