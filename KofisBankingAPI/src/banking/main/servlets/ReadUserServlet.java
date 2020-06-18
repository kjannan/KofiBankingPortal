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
import banking.main.daos.DBQuery;

public class ReadUserServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException{
		System.out.println(this.getServletName()+" is INITILIAZED");
		super.init();
	}
	
	//also use doPost but take in different parameters
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException{
		
		//Get All users
		try (Connection conn = DBConnection.getConnection()){
			
			String currentRole = DBQuery.currentUser.getRole();
			if (currentRole.equals("BankOwner") || currentRole.equals("admin") || currentRole.equals("employee")) {
				DBQuery.getUsers(conn, res); //outputs all users to http servlet response
			}else {
				DBQuery.getCurrentUser(res);//outputs the current user logged in
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
		super.destroy();
	}
	
}
