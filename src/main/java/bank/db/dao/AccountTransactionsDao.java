package bank.db.dao;

import bank.exceptions.NotFound;
import bank.model.domain.AccountTransactions;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionsDao {

    Optional<List<AccountTransactions>> findByAccountId(Long accountId);

    Optional<AccountTransactions> add(AccountTransactions accountTransactions);

}
