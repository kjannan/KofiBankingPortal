package banking.main.api;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import banking.main.daos.DBConnection;
import banking.main.daos.DBQuery;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Bank of Kofi Annan");
		
		Scanner scan = new Scanner(System.in);
		try (Connection conn = DBConnection.getConnection()){
			boolean result = false;
			do {
				System.out.println("Username: ");
				String user = scan.nextLine();
				System.out.println("Password: ");
				String password_input =scan.nextLine();
				result = DBQuery.loginCheck(conn, user, password_input);
			}while(!result);
		}catch(SQLException | ClassNotFoundException e) {
			System.out.println("Database Connection error");
			e.printStackTrace();
		}finally {
			System.out.println("Closing connections....");
			scan.close();
			System.out.println("Bye!");
		}

//		for (String command : args) {
//			System.out.println(command);
//		}
		
	}

}
