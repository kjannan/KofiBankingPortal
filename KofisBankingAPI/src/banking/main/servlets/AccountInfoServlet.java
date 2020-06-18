package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banking.main.api.Account;
import banking.main.daos.DBConnection;
import banking.main.daos.DBQuery;

public class AccountInfoServlet extends HttpServlet{


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
			List<Account> accounts = DBQuery.getAccountInfo(conn);
			PrintWriter out = res.getWriter();
			printTableHeader(out);
			for(Account account:accounts) {
				printTableRow(out, account.getAccountNum(),account.getType(),account.getBalance());
			}
			out.println("</table>"); //end table
			
			if ( currentRole.equals("premium") ) {
				out.println("<a href=\"http://localhost:8080/KofisBankingAPI/PremiumUserHomepage.html\" class=\"button\">HOME</a>");
			}else {
				out.println("<a href=\"http://localhost:8080/KofisBankingAPI/StandardUserHomepage.html\" class=\"button\">HOME</a>");
			}			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void printTableHeader(PrintWriter out) {
		//print out table of first name, last name, and email
		out.println("<style>");
		out.println("table {border-collapse: collapse;}");
		out.println("table, th, td {border: 1px solid black;}");
		out.println("</style>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>  Account Number </th>");
		out.println("<th> Account Type  </th>");
		out.println("<th> Balance </th>");
		out.println("</tr>");
	}
	
	private void printTableRow(PrintWriter out, int accountNo, String type, double balance) {
		out.println("<tr>"); //print row
		//ACCOUNT NUMBER PRINT
		out.println("<td>"+ accountNo +"</td>");
		//ACCOUNT TYPE PRINT
		out.println("<td>"+ type +"</td>");
		//BALANCE PRINT
		out.println("<td>"+ balance +"</td>");
		out.println("</tr>"); //end row
	
		
	}
	public void destroy() {
		System.out.println(this.getServletName()+" is DEALOCCATED");
		super.destroy();
	}
	
}
