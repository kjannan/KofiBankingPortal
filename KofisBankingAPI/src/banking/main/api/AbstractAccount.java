package banking.main.api;

public abstract class AbstractAccount {
	  private int accountNum; // primary key
	  private double balance;  // not null
	  private AccountStatus status;
	  private AccountType type;
	  
	  public enum AccountType {
	  Checking, Savings
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

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
	  
}