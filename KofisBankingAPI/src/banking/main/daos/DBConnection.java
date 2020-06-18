package banking.main.daos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String url = "jdbc:oracle:thin:@localhost/orcl";
	private static String username = "kofi";
	private static String password = "oracle";
	private static Connection conn = null;
	

	private DBConnection() {
		super();	
	
	}
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("Connection to database " + url );
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Successfull");
		return conn;
	}
		
}
