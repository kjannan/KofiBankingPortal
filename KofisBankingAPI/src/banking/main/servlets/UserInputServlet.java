package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserInputServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		System.out.println(this.getServletName() + " IS INSTANTIATED!");
		super.init();
	}
	
	protected void service(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException{
		System.out.println(this.getServletName() + " SERVICE METHOD CALLED!");
		
		
		//we must get the parameters out of the http request
		String username = req.getParameter("username");
		String password = req.getParameter("password");
			if(username!=null && password!=null) {
				PrintWriter out = res.getWriter();
				out.printf("Login in user: %s with password %s \n", username, password);
				
			}
		
		
	}
	public void destroy() {
		System.out.println(this.getServletName() + " DESTROY METHOD CALLED!");
		super.destroy();
	}
}
