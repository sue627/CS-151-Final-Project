package control;

import model.Account;

public class BackToDetailMessage implements Message {
    private Account account;

    public BackToDetailMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
