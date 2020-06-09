package banking.main.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
	
	//private Connection connection;

//	public DBQuery(Connection connection) {
//		super();
//		this.connection = connection;
//	}
	
	public static boolean loginCheck(Connection conn, String username, String password) {
		
		boolean result = false;
		try {
			System.out.println("loging in.....");
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT username, passcode FROM BANK_USERS "
					+ "WHERE username = '" + username +"' AND passcode = '" + password + "'");
			if(resultSet.next()) {
//				String data = resultSet.getString("username");
//				System.out.println(data);
//				data = resultSet.getString("passcode");
//				System.out.println(data);
				System.out.println("correct credentials");
				result=true;
				
			}else {
				System.out.println("Username or Password Invalid");
			}
		}catch(SQLException e) {
			System.out.println("Username or Password Invalid");
		}
		return result;
	}
	

}
