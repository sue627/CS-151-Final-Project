package model;

public class Credit extends Account {
    private String accountNumber;
    private final String TYPE = "Credit";

	public void setAccountNumber() {
		accountNumber=super.getPhoneNumber()+3;
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
