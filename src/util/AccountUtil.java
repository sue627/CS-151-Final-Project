package util;
import java.util.ArrayList;
import java.util.Collections;

import model.*;

public class AccountUtil {
    public static ArrayList<Account>account_list=null;
    
    //initialize the util
   
    //check if we have the accont or not
    public static boolean hasAccount(String accountNumber) {
    	for(Account a:account_list) {
    		if(a.accountNumber().equals(accountNumber))
    			return true;
    	}
    	return false;
    }
    
    //match the account
    public static Account matcher(String accountNumber) {
    	for(Account a:account_list) {
    		if(a.accountNumber().equals(accountNumber)) {
    			return a;
    		}
    	}
    	return null;
    }

    public static ArrayList<Account> getAllRealatedAccounts(String phoneNumber) {
    	ArrayList<Account> relatedAccounts = new ArrayList<>();
    	for(Account a: account_list) {
    		if(a.accountNumber().equals(phoneNumber + "1")) {
    			relatedAccounts.add(a);
			}
    		if(a.accountNumber().equals(phoneNumber + "2")) {
    			relatedAccounts.add(a);
			}
    		if(a.accountNumber().equals(phoneNumber + "3")) {
    			relatedAccounts.add(a);
			}
		}

		Collections.sort(relatedAccounts);

    	return relatedAccounts;
	}
    
    public static boolean password_matcher(String password) {
    	if(password==null) {
    		return false;
    	}
    	for(Account a:account_list) {
    		 if(password.equals(a.getPassword())) {
    			 return true;
    		 }
    	}
    	return false;
    }

    public static String checkAccountType(Account a) {
    	String tmp = null;
    	if(a.getClass() == Checking.class) {
    		tmp = "Checking";
		}
    	if(a.getClass() == Saving.class) {
    		tmp = "Saving";
		}
    	if(a.getClass() == Credit.class) {
    		tmp = "Credit";
		}
    	return tmp;
	}
    
    public static boolean phoneNumber_matcher(String phoneNumber) {
    	if(phoneNumber==null)return false;
    	
    	for(Account a:account_list) {
    		if(phoneNumber.equals(a.getPhoneNumber())) return true;
    	}
    	
    	return false;
    }
    
    public static boolean moneyValid(double money) {
    	if(money <= 0) {
    		return false;
		} else {
    		return true;
		}
    }
    
    public static boolean hasAlert(Account myAccount, String otheraccountNo,double money) {
    	if(!moneyValid(money)) {
    		return true;
    	}
    	if(!hasAccount(otheraccountNo)) {
    		return true;
    	}
    	
    	 if(myAccount.getBalance()-money<0) {
    		 return true;
    	 }
    	 
    	 return false;
    }
}
