package bank.exceptions;

import bank.model.domain.TransactionHistory;

public class TransactionNotFound extends NotFound {

    public TransactionNotFound() {
        super("Transaction doesn't exist");
    }

    public TransactionNotFound(String message) {
        super(message);
    }

    public TransactionNotFound(TransactionHistory transactionHistory) {
        super("Such transaction history doesn't exist " + transactionHistory);
    }

}