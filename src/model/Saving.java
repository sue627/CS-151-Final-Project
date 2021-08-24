package model;

public class Saving extends Account{
	    private String accountNumber;
	    private final String TYPE = "Saving";

		public void setAccountNumber() {
			accountNumber=super.getPhoneNumber()+2;
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
