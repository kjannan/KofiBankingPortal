package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection conn; //make sure driver software is included in lib
	//we create an init method, service method -->doGet() method
	//OR doPost()
	//create a destroy method
	
	public void init() throws ServletException{
		System.out.println(this.getServletName()+" is INITILIAZED");
		super.init();
		
		//we will establish a jdbc connection once
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521/orcl";
			String username = "kofi";
			String password = "oracle";
			conn =  DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection Error");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("JDBC driver error");
			e.printStackTrace();
		}
		
	}
	
	//since we are creating a user and changing the database
	//we must use the POST HTTP method ...we do so in a servlet with the doPost()
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException{
		
		//We will eventually us a SQL insert statement here
		//the info that we are posting/inserting to the database comes from parameters
		
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String username = req.getParameter("username");
		String role = req.getParameter("role");
		
		//then we create an insert statement
		try {
			Statement statement = conn.createStatement();
			String sql = "INSERT INTO bank_users(username, passcode, first_name,last_name,email, user_role) "
					+ "VALUES('"
					+ username +"', '"
					+ password +"', '"
					+ firstname +"', '"
					+ lastname +"', '"
					+ email +"', '"
					+ role+"')";
			System.out.println(sql);
			
			PrintWriter out = res.getWriter();
			
			
			int result = statement.executeUpdate(sql); //execute update returns number of row affected
			
			if (result>0) {
				out.println("<h1> User Created Successfully </h1>");
			}else {
				out.println("<h1> Error Creating User </h1>");
			}
			out.println("<a href=\"http://localhost:8080/KofisBankingAPI/AdminHomepage.html\" class=\"button\">HOME</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Sql Connection Error");
			e.printStackTrace();
		}
		
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName()+" is DEALOCCATED");
		super.destroy();
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Closing sql connection");
			e.printStackTrace();
		}
	}
}
