package banking.main.daos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import banking.main.api.User;
import banking.main.api.Account;

public class DBQuery {
	
	//private Connection connection;

	public static User currentUser = new User();
	
	public static boolean loginCheck(Connection conn, String username, String password) {
		
		boolean result = false;
		try {
			System.out.println("loging in.....");
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM BANK_USERS "
					+ "WHERE username = '" + username +"' AND passcode = '" + password + "'");
			if(resultSet.next()) {
//				String data = resultSet.getString("username");
//				System.out.println(data);
				setCurrentUser(resultSet);//sets variables for the Current user logged in
				result=true;
				
			}else {
				System.out.println("Username or Password Invalid");
			}
		}catch(SQLException e) {
			System.out.println("Problem with logging in :-(");
		}
		return result;
	}
	

	public static void getUsers(Connection conn, HttpServletResponse res) {
		try {
			
			Statement statement = conn.createStatement();
			String sql = "SELECT * FROM bank_users ORDER BY user_id";
			System.out.println(sql);
			
			//res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			
			
			ResultSet resultSet = statement.executeQuery(sql); //execute query returns rows 
			
			//print out table of first name, last name, and email
			printTableHeader(out);
			
			while (resultSet.next()) {
				printTableRow(out, Integer.parseInt(resultSet.getString("user_id")), resultSet.getString("first_name"),resultSet.getString("last_name"), 
						resultSet.getString("username") , resultSet.getString("email") , resultSet.getString("user_role"));
				
			}
			
			out.println("</table>"); //end table
			out.println("<a href=\"http://localhost:8080/KofisBankingAPI/AdminHomepage.html\" class=\"button\">HOME</a>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sql Connection Error");
			e.printStackTrace();
		}
	}



	public static void setCurrentUser(ResultSet resultSet) throws SQLException {
		
		currentUser.setUserId(resultSet.getInt("user_id"));
		currentUser.setUsername(resultSet.getString("username"));
		currentUser.setPasscode(resultSet.getString("passcode"));
		currentUser.setFirstName(resultSet.getString("first_name"));
		currentUser.setLastName(resultSet.getString("last_name"));
		currentUser.setEmail(resultSet.getString("email"));
		currentUser.setRole(resultSet.getString("user_role"));
	}
	
	public static void getCurrentUser(HttpServletResponse res) throws IOException{
		PrintWriter out = res.getWriter();
		printTableHeader(out);
		printTableRow(out, currentUser.getUserId(), currentUser.getFirstName(), currentUser.getLastName(), 
				currentUser.getUsername(), currentUser.getEmail(), currentUser.getRole());
		out.println("</table>"); //end table
		
		if ( (currentUser.getRole()).equals("premium") ) {
			out.println("<a href=\"http://localhost:8080/KofisBankingAPI/PremiumUserHomepage.html\" class=\"button\">HOME</a>");
		}else {
			out.println("<a href=\"http://localhost:8080/KofisBankingAPI/StandardUserHomepage.html\" class=\"button\">HOME</a>");
		}
		
		
	}
	
	public static int getLatestAccount(Connection conn) throws SQLException {
		Statement statement = conn.createStatement();
		String sql = "SELECT MAX(account_no) as max FROM accounts";
		ResultSet resultSet = statement.executeQuery(sql);
		int accountNo = 0;
		while (resultSet.next()) {
			accountNo = resultSet.getInt("max");
		}
		
		return accountNo;
	}
	
	public static List<Account> getAccountInfo(Connection conn) throws SQLException{
		List<Account> allAccounts = new ArrayList<>();
		
		Statement statement = conn.createStatement();
		String sql = ("SELECT BA.user_id, BA.account_no, A.account_type, A.balance "
					+"FROM bank_users_accounts BA "
					+"JOIN accounts A "
					+"ON BA.account_no = A.account_no "
					+"WHERE BA.user_id = "+ currentUser.getUserId()); //selects a join of accounts table and relation table
		System.out.println(sql);			
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt("user_id");
			int accountNo = resultSet.getInt("account_no");
			String type = resultSet.getString("account_type");
			double balance = resultSet.getDouble("balance");
			
			Account account = new Account(accountNo, balance, type);
			allAccounts.add(account);
		}
		return allAccounts;
	}
	private static void printTableHeader(PrintWriter out) {
		//print out table of first name, last name, and email
		out.println("<style>");
		out.println("table {border-collapse: collapse;}");
		out.println("table, th, td {border: 1px solid black;}");
		out.println("</style>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>  User ID </th>");
		out.println("<th> First Name </th>");
		out.println("<th> Last Name </th>");
		out.println("<th> User Name </th>");
		out.println("<th> Email </th>");
		out.println("<th> Role </th>");
		out.println("</tr>");
	}
	
	private static void printTableRow(PrintWriter out, int id, String fname, String lname, String uname, String email, String role) {
		out.println("<tr>"); //print row
		//USER ID PRINT
		out.println("<td>"+ id +"</td>");
		//FIRST NAME PRINT
		out.println("<td>"+ fname +"</td>");
		//LAST NAME PRINT
		out.println("<td>"+ lname +"</td>");
		//USERNAME PRINT
		out.println("<td>"+ uname +"</td>");
		//EMAIL PRINT
		out.println("<td>"+ email +"</td>");
		//USER ROLE PRINT
		out.println("<td>"+ role +"</td>");
		
		out.println("</tr>"); //end row
	
		
	}
}
