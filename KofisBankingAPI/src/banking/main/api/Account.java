package banking.main.api;

public class Account {
	  private int accountNum; // primary key
	  private double balance;  // not null
	  private String type;
	  
	 
	public Account() {
		super();
	}
	
	public Account(int accountNum, double balance, String type) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.type = type;
	}

	public int getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	  
}