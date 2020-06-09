package banking.main.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
//	private String url; //= "jdbc:oracle:thin:@localhost/orcl";
//	private String username; //= "kofi";
//	private String password; //="oracle";
	private Connection connection;
	
	//CONSTRUCTOR
	public DBConnection(String url, String username, String password) throws SQLException {
		super();
//		this.url = url;
//		this.username = username;
//		this.password = password;
		System.out.println("Connection to database " + url );
		this.connection = DriverManager.getConnection(url, username, password);
		System.out.println("Successfull");
		
	}
	
	
	public Connection openConnection() {
		return connection;
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
	}
	
}
