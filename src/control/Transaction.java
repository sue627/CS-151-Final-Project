package control;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import model.*;
import util.*;

public class Transaction {
	private String transactionInfo;
	private String transactionInfo2;
	private String time;
	private double currentBalance;
	private String type;
	private double amount;
	private Account sourceAccount;
	private Account targetAccount;



	public String getTransactionInfo() {
		return transactionInfo;
	}

	public String getTransactionInfo2() {
		return transactionInfo2;
	}

	public String getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public double getAmount() { return amount; }

	public boolean deposit(Account account, double money) {
		if(AccountUtil.moneyValid(money)) {
			account.setBalance(money);
			time = getStringDate();
			transactionInfo = "Money deposit";
			currentBalance = account.getBalance();
			type = "Deposit";
			amount = money;
			sourceAccount = account;
			return true;
		} else {
			return false;
		}
	}

	public boolean withdraw(Account account, double money) {
		if(!AccountUtil.moneyValid(money)) {
			return false;
		} else if(account.getBalance() - money < 0) {
			return false;
		} else {
			account.setBalance(0 - money);
			time = getStringDate();
			transactionInfo = "Money withdraw";
			currentBalance = account.getBalance();
			type = "Withdraw";
			amount = money;
			sourceAccount = account;
			return true;
		}
	}

	public boolean transferBetween(Account sourceAccount, Account targetAccount, double money) {
		if(!AccountUtil.moneyValid(money)) {
			return false;
		} else if(sourceAccount.getBalance() - money < 0) {
			return false;
		} else {
			sourceAccount.setBalance(0 - money);
			targetAccount.setBalance(money);
			time = getStringDate();
			transactionInfo = "Transfer money to account: " + targetAccount.accountNumber();
			currentBalance = sourceAccount.getBalance();
			transactionInfo2 = "Transfer money from account: " + sourceAccount.accountNumber();
			type = "TransferOut";
			amount = money;
			this.sourceAccount = sourceAccount;
			this.targetAccount = targetAccount;
			return true;
		}
	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * Below is used for if want to save transactions into a .txt file
	 * Changes(recomment) is also needed in AccountController, DetailView, Main
	 */
//	public void saveTransaction(File f, int targetAccount) {
//		try{
//			f.createNewFile();
//			BufferedWriter out = new BufferedWriter(new FileWriter(f));
//			if(targetAccount == 1) {
//				out.write("Type: " + this.type + " | " + "Info: " + this.transactionInfo2 + " | " + "Time: " + this.time + " | " + "Current balance: " + this.currentBalance + " | " + "Amount: " + this.amount + "\n");
//			} else {
//				out.write("Type: " + this.type + " | " + "Info: " + this.transactionInfo + " | " + "Time: " + this.time + " | " + "Current balance: " + this.currentBalance + " | " + "Amount: " + this.amount + "\n");
//			}
//			out.flush();
//			out.close();
//		} catch (Exception p) {
//			p.printStackTrace();
//		}
//	}
//
//	public ArrayList<Transaction> getTransactionsOf(File f, Account account) {
//		ArrayList<Transaction> transactions = new ArrayList<>();
//		try {
//			Scanner scanner = new Scanner(f);
//			while(scanner.hasNextLine()) {
//				String line = scanner.nextLine();
//				String parts[] = line.split("\\|");
//				String fInfos[] = parts[1].split(":");
//				String fInfo = (fInfos[1] + fInfos[2]).trim();
//				String accountNumber = fInfos[2].replaceAll(" ","");
//				if(accountNumber.equals(account.accountNumber())) {
//					String fTypes[] = parts[0].split(":");
//					String fType = fTypes[1].replaceAll(" ","");
//					String fTimes[] = parts[2].split(":");
//					String fTime = (fTimes[1] + fTimes[2] + fTimes[3]).trim();
//					String fCurrentbalances[] = parts[3].split(":");
//					String fCurrentbalance = fCurrentbalances[1].replaceAll(" ","");
//					String fAmounts[] = parts[4].split(":");
//					String fAmount = fAmounts[1].replaceAll(" ","");
//					Transaction transaction = new Transaction();
//					transaction.transactionInfo = fInfo;
//					transaction.amount = Double.parseDouble(fAmount);
//					transaction.currentBalance = Double.parseDouble(fCurrentbalance);
//					transaction.time = fTime;
//					transaction.type = fType;
//
//					transactions.add(transaction);
//				}
//			}
//		} catch (Exception p) {
//			p.printStackTrace();
//		}
//
//		return transactions;
//	}


}
