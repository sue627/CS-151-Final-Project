package control;
import model.*;
import util.AccountUtil;
import view.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;


public class AccountController {
	private BlockingDeque<Message> queue;
	private LoginView loginView;
	private AccountsView accountsView;
	private DetailView detailView;
	private TransactionView transactionView;
    private ArrayList<Account>accountList=null;
    private Map<String,String>personalBook=null;
    private Scanner sc=null;
    private String str=null;
    private String phoneNumber;
    private String password;
	private List<Valve> valves = new LinkedList<>();
	private File bankAccount;
	private File userAccount;
//	private File transactionFile;
    
    public AccountController(File BankAccount,File UserAccount, LoginView loginView, BlockingDeque<Message> queue) {
    	accountList = new ArrayList<>();
    	personalBook = new HashMap<>();
    	this.bankAccount = BankAccount;
    	this.userAccount = UserAccount;
//    	this.transactionFile = transactionFile;
    	accountList=getAllBankAccount(BankAccount);
    	personalBook=getAllPersonal(UserAccount);
    	this.queue = queue;
    	this.loginView = loginView;
    	valves.add(new DoLogin());
    	valves.add(new DoLogout());
    	valves.add(new DoMyAccount());
    	valves.add(new DoDetailView());
    	valves.add(new DoSignup());
    	valves.add(new DoAddAccount());
    	valves.add(new DoPay());
    	valves.add(new DoTransfer());
    	valves.add(new DoDeposit());
    	valves.add(new DoGoBackDetail());
    	valves.add(new DoShowAll());
    }
    
    public ArrayList<Account>getAccountList(){
    	return accountList;
    }
    
	public ArrayList<Account> getAllBankAccount(File f){
		Account account=null;
		try {
		   sc=new Scanner(f);
		   while(sc.hasNextLine()) {
			   str=sc.nextLine();
			   String str1[]=str.split(":");
			   String s1=str1[0];
			   String s2=str1[1];
			   double cash=Double.parseDouble(s2);
			   String tmp =s1.substring(s1.length() - 1,s1.length());
			   String phoneNumber = s1.substring(0,s1.length() - 1);
			   if(tmp.equals("1")) {
				   account=new Checking();
				   account.setAccountNumber(s1);
				   account.setBalance(cash);
				   account.setPhoneNumber(phoneNumber);
			   }

			   if(tmp.equals("2")) {
				   account=new Saving();
				   account.setAccountNumber(s1);
				   account.setBalance(cash);
				   account.setPhoneNumber(phoneNumber);
			   }
			   
			   
			   if(tmp.equals("3")) {
				   account=new Credit();
				   account.setAccountNumber(s1);
				   account.setBalance(cash);
				   account.setPhoneNumber(phoneNumber);
			   }
			   accountList.add(account);
		   }
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
		
		return accountList;
	}
	
	
	public Map<String,String>getAllPersonal(File f){
    	try {
			sc=new Scanner(f);
			while(sc.hasNextLine()) {
				str=sc.nextLine();
				String str1[]=str.split(":");
				phoneNumber=str1[0].trim();
				password=str1[1].replaceAll(" ","");
				personalBook.put(phoneNumber,password);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
		
		return personalBook;
		
	}
	
	public void updateRecord(Account old,String phoneNumber,String password) {
		 for(Account a:accountList) {
			 if(a.getPhoneNumber().equals(old.getPhoneNumber())) {
				 a.setAccountNumber(phoneNumber);
				 a.setPassword(password);
			 }
		 }
		 
		 personalBook.remove(phoneNumber);
		 personalBook.put(phoneNumber,password);
	}
	
	public void save(File BankAccount,File UserAccount) {
	   try {
		    FileOutputStream writer1 = new FileOutputStream(BankAccount);
		    FileOutputStream writer2= new FileOutputStream(UserAccount);
	   }catch(IOException e) {
		e.printStackTrace();
	   }

	   try {
	   		BankAccount.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(BankAccount));
			for(Account account : accountList) {
				out.write(account.accountNumber() + ":   " + account.getBalance() + "\n");
				out.flush();
			}
		   out.close();
	   } catch(Exception e) {
			e.printStackTrace();
	   }

		try {
			UserAccount.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(UserAccount));
			for(Map.Entry<String, String> entry : personalBook.entrySet()) {
				out.write(entry.getKey() + " :   " + entry.getValue() + "\n");
				out.flush();
			}
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	           
	}

	private interface Valve {
		public ValveResponse execute(Message message);
	}

	//actions for login
	public class DoLogin implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != LoginMessage.class) {
				return ValveResponse.MISS;
			}
			LoginMessage tmp = (LoginMessage) message;
			if(!personalBook.containsKey(tmp.getUserName())) {
				loginView.updateLoginStatus(2);
			} else if (personalBook.get(tmp.getUserName()).equals(tmp.getPassword())) {
				loginView.updateLoginStatus(1);
				AccountUtil.account_list = accountList;
				ArrayList<Account> relatedAccounts = AccountUtil.getAllRealatedAccounts(tmp.getUserName());
				loginView.dispose();
				accountsView = new AccountsView(tmp.getUserName(), queue, relatedAccounts);
			} else {
				loginView.updateLoginStatus(2);
			}

			return ValveResponse.EXECUTED;
		}
	}

	//actions for logout
	public class DoLogout implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != LogoutMessage.class) {
				return ValveResponse.MISS;
			}
			LogoutMessage tmp = (LogoutMessage) message;
			if(tmp.getSender().equals("AccountsView")) {
				accountsView.dispose();
			} else if(tmp.getSender().equals("DetailView")) {
				detailView.dispose();
			} else if(tmp.getSender().equals("TransactionView")) {
				transactionView.dispose();
			}
			loginView = new LoginView(queue);
			return ValveResponse.EXECUTED;
		}
	}

	//actions for going back to MyAccount
	public class DoMyAccount implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != MyAccountMessage.class) {
				return ValveResponse.MISS;
			}
			MyAccountMessage tmp = (MyAccountMessage) message;
			AccountUtil.account_list = accountList;
			ArrayList<Account> relatedAccounts = AccountUtil.getAllRealatedAccounts(tmp.getUserName());
			detailView.dispose();
			accountsView = new AccountsView(tmp.getUserName(), queue, relatedAccounts);

			return ValveResponse.EXECUTED;
		}
	}

	//actions for going to DetailView
	public class DoDetailView implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != DetailMessage.class) {
				return ValveResponse.MISS;
			}
			DetailMessage tmp = (DetailMessage) message;
//			Transaction tmp2 = new Transaction();
//			tmp.getAccount().setTransactions(tmp2.getTransactionsOf(transactionFile, tmp.getAccount()));
			accountsView.dispose();
			detailView = new DetailView(tmp.getAccount(), queue);
			return ValveResponse.EXECUTED;
		}
	}

	//actions for signing up
	public class DoSignup implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != SignupMessage.class) {
				return ValveResponse.MISS;
			}
			SignupView tmp = new SignupView();
			String username = null;
			boolean flag = true;
			while(flag) {
				System.out.println();
				if(tmp.getSignal() == 1) {
					username = tmp.getUserName();
					password = tmp.getPassword();
					flag = false;
				} else if(tmp.getSignal() == 2) {
					flag = false;
					tmp.dispose();
					return ValveResponse.EXECUTED;
				}
			}
			if(personalBook.containsKey(username)) {
				JDialog d = new JDialog(loginView,null,true);
				d.setBounds(500,350,300,150);

				JLabel l = new JLabel("The username has already exist!");
				l.setBounds(80,70, 250, 30);
				d.add(l);

				d.setVisible(true);
				d.setLayout(null);

				tmp.dispose();
			} else {
				personalBook.put(username, password);
				save(bankAccount, userAccount);
				JDialog d = new JDialog(loginView,null,true);
				d.setBounds(500,350,300,150);

				JLabel l = new JLabel("Create success!");
				l.setBounds(80,70, 250, 30);
				d.add(l);

				d.setVisible(true);
				d.setLayout(null);

				tmp.dispose();
			}
			return ValveResponse.EXECUTED;
		}
	}

	//actions for adding account
	public class DoAddAccount implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != AddAccountMessage.class) {
				return ValveResponse.MISS;
			}
			AddAccountView tmp = new AddAccountView(accountsView.getPhoneNumber());
			boolean flag = true;
			int type = 0;
			String phoneNumber = null;
			while(flag) {
				System.out.println();
				if(tmp.getAccountype() != 0) {
					type = tmp.getAccountype();
					phoneNumber = tmp.getPhoneNumber();
					flag = false;
				}
			}
			AccountUtil.account_list = accountList;
			if(type == 1) {
				String accountNo = phoneNumber + "1";
				if(AccountUtil.hasAccount(accountNo)) {
					JDialog d = new JDialog(loginView,null,true);
					d.setBounds(500,350,300,150);

					JLabel l = new JLabel("The account has already exist!");
					l.setBounds(80,70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
				} else {
					Account account = new Checking();
					account.setAccountNumber(accountNo);
					account.setBalance(0);
					accountList.add(account);
					save(bankAccount,userAccount);
					JDialog d = new JDialog(loginView,null,true);
					d.setBounds(500,350,300,150);

					JLabel l = new JLabel("Create success!");
					l.setBounds(80,70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
					accountsView.dispose();
					accountsView = new AccountsView(phoneNumber,queue,AccountUtil.getAllRealatedAccounts(phoneNumber));
				}
			} else if (type == 2) {
				String accountNo = phoneNumber + "2";
				if(AccountUtil.hasAccount(accountNo)) {
					JDialog d = new JDialog(loginView,null,true);
					d.setBounds(500,350,300,150);

					JLabel l = new JLabel("The account has already exist!");
					l.setBounds(80,70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
				} else {
					Account account = new Saving();
					account.setAccountNumber(accountNo);
					account.setBalance(0);
					accountList.add(account);
					save(bankAccount, userAccount);
					JDialog d = new JDialog(loginView, null, true);
					d.setBounds(500, 350, 300, 150);

					JLabel l = new JLabel("Create success!");
					l.setBounds(80, 70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
					accountsView.dispose();
					accountsView = new AccountsView(phoneNumber,queue,AccountUtil.getAllRealatedAccounts(phoneNumber));
				}
			} else if (type == 3) {
				String accountNo = phoneNumber + "3";
				if (AccountUtil.hasAccount(accountNo)) {
					JDialog d = new JDialog(loginView, null, true);
					d.setBounds(500, 350, 300, 150);

					JLabel l = new JLabel("The account has already exist!");
					l.setBounds(80, 70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
				} else {
					Account account = new Credit();
					account.setAccountNumber(accountNo);
					account.setBalance(0);
					accountList.add(account);
					save(bankAccount, userAccount);
					JDialog d = new JDialog(loginView, null, true);
					d.setBounds(500, 350, 300, 150);

					JLabel l = new JLabel("Create success!");
					l.setBounds(80, 70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp.dispose();
					accountsView.dispose();
					accountsView = new AccountsView(phoneNumber,queue,AccountUtil.getAllRealatedAccounts(phoneNumber));
				}
			}

			return ValveResponse.EXECUTED;
		}
	}

	//actions for paying
	public class DoPay implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != PayMessage.class) {
				return ValveResponse.MISS;
			}
			JDialog d = new JDialog(accountsView, null, true);
			d.setBounds(500, 350, 300, 150);

			JLabel l = new JLabel("Oops, coming soon!");
			l.setBounds(80, 70, 250, 30);
			d.add(l);

			d.setVisible(true);
			d.setLayout(null);


			return ValveResponse.EXECUTED;
		}
	}

	public class DoTransfer implements Valve{
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != TransferMessage.class) {
				return ValveResponse.MISS;
			}
			TransferView tmp2 = new TransferView();

			String amount = null;
			String targetAccount = null;
			boolean flag = true;
			while(flag) {
				System.out.println();
				if(tmp2.getSignal() == 1) {
					amount = tmp2.getPayAmount();
					targetAccount = tmp2.getTargetAccount();
					flag = false;
				} else if(tmp2.getSignal() == 2) {
					flag = false;
					tmp2.dispose();
					return ValveResponse.EXECUTED;
				}
			}
			if(Double.parseDouble(amount) <= 0) {
				JDialog d = new JDialog(accountsView, null, true);
				d.setBounds(500, 350, 300, 150);

				JLabel l = new JLabel("Invalid money!");
				l.setBounds(80, 70, 250, 30);
				d.add(l);

				d.setVisible(true);
				d.setLayout(null);

				tmp2.dispose();
			} else {
				boolean accountValid = false;
				TransferMessage tmp1 = (TransferMessage) message;
				Transaction transaction = new Transaction();
				double money = Double.parseDouble(amount);
				Account tagAc = null;
				for(Account a : accountList) {
					if(a.accountNumber().equals(targetAccount)) {
						tagAc = a;
						accountValid = true;
					}
				}
				if(accountValid) {
					if(transaction.transferBetween(tmp1.getAccount(),tagAc, money) == true) {
						tmp1.getAccount().addTransaction(transaction);
//				transaction.saveTransaction(transactionFile, 0 );
//				tmp1.getAccount().setTransactions(transaction.getTransactionsOf(transactionFile,tmp1.getAccount()));
						JDialog d = new JDialog(accountsView, null, true);
						d.setBounds(500, 350, 300, 150);

						JLabel l = new JLabel("Transfer success!");
						l.setBounds(80, 70, 250, 30);
						d.add(l);

						d.setVisible(true);
						d.setLayout(null);

						tmp2.dispose();
						detailView.dispose();
						detailView = new DetailView(tmp1.getAccount(),queue);
						save(bankAccount,userAccount);
					}else {
						JDialog d = new JDialog(accountsView, null, true);
						d.setBounds(500, 350, 300, 150);

						JLabel l = new JLabel("Not enough money!");
						l.setBounds(80, 70, 250, 30);
						d.add(l);

						d.setVisible(true);
						d.setLayout(null);

						tmp2.dispose();
					}

				} else {
					JDialog d = new JDialog(accountsView, null, true);
					d.setBounds(500, 350, 300, 150);

					JLabel l = new JLabel("Target account does not exist!");
					l.setBounds(80, 70, 250, 30);
					d.add(l);

					d.setVisible(true);
					d.setLayout(null);

					tmp2.dispose();
				}

			}

			return ValveResponse.EXECUTED;
		}
	}

	public class DoDeposit implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != DepositMessage.class) {
				return ValveResponse.MISS;
			}
			JDialog d = new JDialog(accountsView, null, true);
			d.setBounds(500, 350, 300, 150);

			JLabel l = new JLabel("Oops, coming soon!");
			l.setBounds(80, 70, 250, 30);
			d.add(l);

			d.setVisible(true);
			d.setLayout(null);
			return ValveResponse.EXECUTED;
		}
	}

	public class DoGoBackDetail implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != BackToDetailMessage.class) {
				return ValveResponse.MISS;
			}
			BackToDetailMessage tmp = (BackToDetailMessage) message;
			transactionView.dispose();
			detailView = new DetailView(tmp.getAccount(),queue);
			return ValveResponse.EXECUTED;
		}
	}

	public class DoShowAll implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if(message.getClass() != ShowAllMessage.class) {
				return ValveResponse.MISS;
			}
			ShowAllMessage tmp = (ShowAllMessage) message;
			detailView.dispose();
			transactionView = new TransactionView(tmp.getAccount(), queue);
			return ValveResponse.EXECUTED;
		}
	}

	public void mainLoop() {
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;
		while (response != ValveResponse.FINISH) {
			try {
				message = queue.take();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			for(Valve valve: valves) {
				response = valve.execute(message);

				if(response != ValveResponse.MISS) {
					break;
				}
			}
		}
	}
}
