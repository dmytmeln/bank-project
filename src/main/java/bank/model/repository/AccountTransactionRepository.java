package bank.model.repository;

import bank.exceptions.AccountTransactionNotFound;
import bank.model.domain.AccountTransactions;

import java.util.List;

public interface AccountTransactionRepository {

    List<AccountTransactions> findByAccountId(Long accountId) throws AccountTransactionNotFound;

    AccountTransactions add(AccountTransactions accountTransactions);

}
