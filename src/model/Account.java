package model;


import control.Transaction;

import java.util.ArrayList;

public abstract class Account implements Comparable<Account>{
     private String phoneNumber;
	 private String password;
	 private double balance = 0;
     private ArrayList<Transaction> transactions = new ArrayList<>();
    
     
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int compareTo(Account o) {
		String s1 = this.accountNumber().substring(this.accountNumber().length() - 1, this.accountNumber().length());
		String s2 = o.accountNumber().substring(o.accountNumber().length() - 1, o.accountNumber().length());
		if(Integer.parseInt(s1) < Integer.parseInt(s2)) {
			return -1;
		} else if (Integer.parseInt(s1) > Integer.parseInt(s2)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Account) {
			Account tmp = (Account) obj;
			return this.compareTo(tmp) == 0;
		}
		return false;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}


	public void addTransaction(Transaction transaction) {
		transactions.add(0, transaction);
	}


	public double getBalance() {
		return balance;
	}

	public void setBalance(double money) {
		balance = balance + money;
	}

	public abstract void setAccountNumber(String number);
	
	public abstract void setAccountNumber();
	
    public abstract String accountNumber();


    public abstract String getType();



    
}
