package banking.main.api;

public class User {
	  private int userId; // primary key
	  private String username; // not null, unique
	  private String passcode; // not null
	  private String firstName; // not null
	  private String lastName; // not null
	  private String email; // not null
	  private String userRole;
	
	//DEFAULT CONSTRUCTOR  
	public User() {
		
	}
	
	public User(int userId, String username, String passcode, String firstName, String lastName, String email, String role) {
		this.userId = userId;
		this.username = username;
		this.passcode = passcode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = role;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return userRole;
	}

	public void setRole(String role) {
		this.userRole = role;
	}
	  
	  
	}
