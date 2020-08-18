package it.lidobalneare.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lidobalneare.SHA256;
import it.lidobalneare.db.DBConnect;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet({ "/RegisterServlet", "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entering servlet");
		String email = request.getParameter("email");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if(password1.equals(password2)) {
			String password = SHA256.encode(password1);
			String birthdate = request.getParameter("birthdate");
			String gender = request.getParameter("gender");
			try {
				DBConnect.register(email,password,birthdate,gender,name,surname);
			} catch (Exception e) {
				e.printStackTrace();
				//Registration error
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write("Hello");
		} else {
			// ERROR passwords not equals
		}
		
	}

}
