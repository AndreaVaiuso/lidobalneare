package it.lidobalneare.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String code = request.getParameter("usercode");
    	try {
			DBConnect.unlockAccount(code);
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
			RequestDispatcher view = request.getRequestDispatcher("accountValidationFailure.html");
	    	view.forward(request, response);
		}
    	RequestDispatcher view = request.getRequestDispatcher("accountValidationSuccess.html");
    	view.forward(request, response);
    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String birthdate = request.getParameter("birthdate");
		System.out.println("servlet date: "+ birthdate);
		// Checking that every field is filled.
		String fieldError = "";	// Control string. Stays empty if no error is detected.
		if (email.isEmpty()) fieldError = "email";
		else if (password1.isEmpty()) fieldError = "password";
		else if (password2.isEmpty()) fieldError = "password";
		else if (name.isEmpty()) fieldError = "name";
		else if (surname.isEmpty()) fieldError = "surname";
		else if (birthdate.isEmpty()) fieldError = "birthdate";
		
		if (!fieldError.isEmpty())	// If any field is empty, creates a JSON representing the error.
		{
			String jsonObject = "{ \"type\" : \"typerror\",  \"missingField\" : \"" + fieldError + "\" }";
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.append(jsonObject);
			out.close();
			return;
		}
		else if(password1.equals(password2)) {
			String password = SHA256.encode(password1);
			String gender = request.getParameter("gender");
			try {
				DBConnect.register(email,password,birthdate,gender,name,surname);
			} catch (SQLException e) {
				String jsonObject = "{ \"type\" : \"alreadyexists\" }";
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.append(jsonObject);
				out.close();
				return;
			} catch (MessagingException e2) {
				System.err.println("Error on sending mail: ");
				e2.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String jsonObject = "{ \"type\" : \"success\" }";
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.append(jsonObject);
			out.close();
			return;
		} else {
			String jsonObject = "{ \"type\" : \"passwordsnotequals\" }";
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.append(jsonObject);
			out.close();
			return;
		}
		
	}

}
