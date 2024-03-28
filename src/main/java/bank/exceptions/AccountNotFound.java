package bank.exceptions;

import bank.model.domain.BankAccount;

public class AccountNotFound extends NotFound {

    public AccountNotFound() {
        super("Account doesn't exist");
    }

    public AccountNotFound(String message) {
        super(message);
    }

    public AccountNotFound(BankAccount bankAccount) {
        super("Such account doesn't exist " + bankAccount);
    }

}
