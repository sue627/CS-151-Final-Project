package control;

import model.Account;

public class TransferMessage implements Message {
    private Account account;

    public TransferMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
