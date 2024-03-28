package bank.exceptions;

import bank.model.domain.AccountTransactions;

public class AccountTransactionNotFound extends NotFound {

    public AccountTransactionNotFound(String message) {
        super(message);
    }

}
