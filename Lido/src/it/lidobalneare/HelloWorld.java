package it.lidobalneare;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloWorld() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Cookie cks[] = request.getCookies();
		Cookie c = new Cookie("sessionID","0101010");
		response.addCookie(c);
		String cookies = "";
		if(cks != null) {
			for(int i = 0;i<cks.length;i++) {
				cookies += cks[i].getName() + " " + cks[i].getValue() + "\n";
			}
			writer.println("<html><body><h1>HelloWorld</h1><br/>"+cookies+"</body></html>");
		} else {
			writer.println("<html><body><h1>HelloWorld</h1><br/>Hello visitor</body></html>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
