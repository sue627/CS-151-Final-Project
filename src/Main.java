import control.AccountController;
import control.Message;
import view.LoginView;
import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) {
        BlockingDeque<Message> queue = new LinkedBlockingDeque<>();
        LoginView view = new LoginView(queue);
        File bankAccount = new File("BankAccount.txt");
        File userAccount = new File("UserAccount.txt");
//        File transactions = new File("Transactions.txt");
        AccountController ac = new AccountController(bankAccount, userAccount, view, queue);
        ac.mainLoop();


    }
}
