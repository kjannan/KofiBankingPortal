package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banking.main.daos.DBConnection;
import banking.main.daos.DBManipulation;
import banking.main.daos.DBQuery;

import banking.main.api.User;

public class UpdateUserServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException{
		System.out.println(this.getServletName()+" is INITILIAZED");
		super.init();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException{
		
		System.out.println(this.getServletName() + " POST METHOD CALLED!");
		
		try (Connection conn = DBConnection.getConnection()){//try to get a connection and close when try-block done
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			String firstName = req.getParameter("firstname");
			String lastName = req.getParameter("lastname");
			int userId;
			
			String currentRole = DBQuery.currentUser.getRole();
			if (currentRole.equals("BankOwner") || currentRole.equals("admin")) {//update user by id
				userId = Integer.parseInt(req.getParameter("userId"));//change string to integer; use input form web browser
			}else {
				userId = DBQuery.currentUser.getUserId(); //use current user's id to look up or editing
			}
			User u = new User(userId, username, password, firstName, lastName, email, currentRole);
			
			PrintWriter out = res.getWriter();
			if(DBManipulation.updateUser(u, conn)) {//successful update
				
				switch (currentRole) {
				case "BankOwner":	out.println("<a href=\"http://localhost:8080/KofisBankingAPI/AdminHomepage.html\">"
										+"User Updated! Click here to go back to Admin Home Page</a>");
									break;
									
				case "admin":		out.println("<a href=\"http://localhost:8080/KofisBankingAPI/AdminHomepage.html\">"
									+"User Updated! Click here to go back to Admin Home Page</a>");
									break;
									
				case "standard": 	DBQuery.currentUser = u;//update current user
									out.println("<a href=\"http://localhost:8080/KofisBankingAPI/StandardUserHomepage.html\">"
										+"User Updated! Click here to go back to Standard User Home Page</a>");
									break;
									
				case "premium": 	DBQuery.currentUser = u;//update current user
									out.println("<a href=\"http://localhost:8080/KofisBankingAPI/PremiumUserHomepage.html\">"
										+"User Updated! Click here to go back to Premium User Home Page</a>");
									break;
									
				}
					
			}else{ //error with updating
				
				if(currentRole=="BankOwner" || currentRole=="admin") {
					out.println("<a href=\"http://localhost:8080/KofisBankingAPI/UpdateUserByAdmin.html\">"
							+"Failed to update User by ID! Click here to go back to Update User by Admin Page</a>");
				}else {
					out.println("<a href=\"http://localhost:8080/KofisBankingAPI/UpdateUser.html\">"
							+"Failed to update User Information! Click here to go back to Update User Page</a>");
				}
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName()+" is DEALOCCATED");
		
	}
	
}
