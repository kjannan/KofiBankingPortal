package banking.main.api;


import java.sql.SQLException;
import java.util.Scanner;
import banking.main.daos.DBConnection;
import banking.main.daos.DBQuery;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Bank of Kofi Annan");
		
		String url = "jdbc:oracle:thin:@localhost/orcl";
		String username = "kofi";
		String password ="oracle";
		
		Scanner scan = new Scanner(System.in);
		try {
			boolean result = false;
			DBConnection conn = new DBConnection(url,username,password);
			do {
				System.out.println("Username: ");
				String user = scan.nextLine();
				System.out.println("Password: ");
				String password_input =scan.nextLine();
				result = DBQuery.loginCheck(conn.openConnection(), user, password_input);
			}while(!result);
			conn.closeConnection();
		}catch(SQLException e) {
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
