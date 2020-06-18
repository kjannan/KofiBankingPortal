package banking.main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banking.main.api.Account;
import banking.main.daos.DBConnection;
import banking.main.daos.DBManipulation;
import banking.main.daos.DBQuery;

public class DepositWithdrawalServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//we create an init method, service method -->doGet() method
	//OR doPost()
	//create a destroy method
	
	public void init() throws ServletException{
		System.out.println(this.getServletName()+" is INITILIAZED");
		super.init();
	}
	
	//since we are creating a user and changing the database
	//we must use the POST HTTP method ...we do so in a servlet with the doPost()
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException{
		
		System.out.println(this.getServletName()+" is POST METHOD CALLED!");
		
		//We will eventually us a SQL insert statement here
		//the info that we are posting/inserting to the database comes from parameters
		
		String depositOrWithdrawal = req.getParameter("dw");
		int accountNo = Integer.parseInt(req.getParameter("account"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		//then we create an insert statement
		try (Connection conn = DBConnection.getConnection()){//try to get a connection and close when try-block done{
			PrintWriter out = res.getWriter();
			out.println("<!DOCTYPE html>\n" + 
					"<html>");
			List<Account> accounts = DBQuery.getAccountInfo(conn);
			for(Account account:accounts) {
				if (account.getAccountNum() == accountNo ) {
					double newBalance = account.getBalance();
					if(depositOrWithdrawal.equals("deposit")){
						newBalance += amount;
					}else {
						newBalance -= amount;
					}
					account.setBalance(newBalance);
					DBManipulation.updateBankBalance(account, conn);
					out.println("<h1> "+depositOrWithdrawal +" Successfull!</h1>");
				}
			}
			String currentRole = DBQuery.currentUser.getRole();

			if  (currentRole.equals("premium") ) {
				out.println("<a href=\"http://localhost:8080/KofisBankingAPI/PremiumUserHomepage.html\" class=\"button\">HOME</a>");
			}else {
				out.println("<a href=\"http://localhost:8080/KofisBankingAPI/StandardUserHomepage.html\" class=\"button\">HOME</a>");
			}
			out.println("</html>");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Sql Connection Error");
			e.printStackTrace();
		}
		
		
	}
	
	public void destroy() {
		System.out.println(this.getServletName()+" is DEALOCCATED");
		super.destroy();

	}
}
