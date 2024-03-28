package bank.exceptions;

import bank.model.domain.BankAccount;

public class AccountAlreadyExists extends ItemAlreadyExists {

    public AccountAlreadyExists(String message) {
        super(message);
    }

}