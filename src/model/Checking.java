package model;

public class Checking extends Account {
    private String accountNumber;
    private final String TYPE = "Checking";

	public void setAccountNumber() {
		accountNumber=super.getPhoneNumber()+1;
	}
	public String accountNumber() {
		return accountNumber;
	}

	@Override
	public void setAccountNumber(String number) {
		// TODO Auto-generated method stub
		accountNumber=number;
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
