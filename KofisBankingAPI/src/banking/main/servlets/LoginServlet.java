package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import banking.main.daos.DBConnection;
import banking.main.daos.DBQuery;



public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		System.out.println(this.getServletName() + " IS INSTANTIATED!");
		super.init();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException{
		System.out.println(this.getServletName() + " POST METHOD CALLED!");
		
		
		//we must get the parameters out of the http request
		String username = req.getParameter("username");
		String password = req.getParameter("password");
			if(username!=null && password!=null) {
				// Below is a try-with-resources block
				// It allows us to instantiate some variable for use only inside the try block
				// And then at the end, it will automatically invoke the close() method on the resource
				// The close() method prevents memory leaks
				try (Connection conn = DBConnection.getConnection()){
					PrintWriter out = res.getWriter();
					//Checking username and password against database
					if(DBQuery.loginCheck(conn,username, password)) {//CORRECT PASSWORD redirect to home page
						out.println("Correct password/username");
						//out.wait(1);
						switch (DBQuery.currentUser.getRole()) {
							case "BankOwner":	res.sendRedirect("http://localhost:8080/KofisBankingAPI/AdminHomepage.html");
												break;
							case "admin":		res.sendRedirect("http://localhost:8080/KofisBankingAPI/AdminHomepage.html");
												break;
							case "employee":	res.sendRedirect("http://localhost:8080/KofisBankingAPI/EmployeeHomepage.html");
												break;
							case "standard": 	res.sendRedirect("http://localhost:8080/KofisBankingAPI/StandardUserHomepage.html");
												break;
							case "premium": 	res.sendRedirect("http://localhost:8080/KofisBankingAPI/PremiumUserHomepage.html");
												break;
												
						}
						
						
					}else {
						out.println("<a href=\"http://localhost:8080/KofisBankingAPI/KofiBankLogin.html\">Wrong Username/Password :-( Click here to go back to sign in page</a>");
					}
					//conn.close();
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}
	public void destroy() {
		System.out.println(this.getServletName() + " DESTROY METHOD CALLED!");
		super.destroy();
	}
}
