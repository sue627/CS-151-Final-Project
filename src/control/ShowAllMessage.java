package control;

import model.Account;

public class ShowAllMessage implements Message {
    private Account account;

    public ShowAllMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
