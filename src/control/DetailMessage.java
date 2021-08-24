package control;

import model.Account;

public class DetailMessage implements Message {
    private Account account;

    public DetailMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}
