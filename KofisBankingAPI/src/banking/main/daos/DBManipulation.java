package banking.main.daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import banking.main.api.Account;
import banking.main.api.User;

public class DBManipulation {
	
	public static boolean updateUser(User u, Connection conn) throws SQLException {
		
		System.out.println("updating user.....");
		Statement statement = conn.createStatement();
		String sql ="UPDATE bank_users SET username='"+u.getUsername()+"', passcode='"+u.getPasscode()
					+"', first_name='"+u.getFirstName()+"', last_name='"+u.getLastName()+"', email='"+u.getEmail()+"' "
					+"WHERE user_id="+u.getUserId();
		System.out.println(sql);
		int rowsAffected = statement.executeUpdate(sql);

		return rowsAffected>0;
	}
	
	public static int openAccount(Connection conn, String accountType) throws SQLException { //returns the account number created
		System.out.println("opening " + accountType + " bank account.....");
		Statement statement = conn.createStatement();
		String sql = "INSERT INTO accounts(account_type, balance) "
				+ "VALUES('"
				+ accountType +"', 0)";
		System.out.println(sql);
		int rows = statement.executeUpdate(sql);
		int accountNo = 0;
		if(rows>0) { 
			accountNo = DBQuery.getLatestAccount(conn);	
		}
		return accountNo;
		
	}
	
	public static boolean updateBankUserAccountRelation(User u, Connection conn, int accountNo) throws SQLException {
		int rows = 0;
		System.out.println("updating user/account realtion table.....");
		Statement statement = conn.createStatement();
		String sql = "INSERT INTO bank_users_accounts(account_no, user_id) "
				+ "VALUES("
				+ accountNo +", "
				+ u.getUserId()	+ ")";
		System.out.println(sql);
		rows = statement.executeUpdate(sql);
		return rows>0;
	}
	
	public static boolean updateBankBalance(Account acc, Connection conn) throws SQLException {
		int rows = 0;
		System.out.println("updating bank balance.....");
		Statement statement = conn.createStatement();
		String sql = "UPDATE accounts SET balance ="
				+ acc.getBalance() +" WHERE account_no="+acc.getAccountNum();
		System.out.println(sql);
		rows = statement.executeUpdate(sql);
		return rows>0;
	}
	
}
