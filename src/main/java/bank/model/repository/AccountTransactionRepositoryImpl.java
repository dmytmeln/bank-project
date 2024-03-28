package bank.model.repository;

import bank.db.dao.AccountTransactionsDao;
import bank.exceptions.AccountTransactionNotFound;
import bank.model.domain.AccountTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("AccountTransactionRepositoryImpl")
public class AccountTransactionRepositoryImpl implements AccountTransactionRepository {

    private final AccountTransactionsDao accountTransactionsDao;

    @Autowired
    public AccountTransactionRepositoryImpl(@Qualifier("JdbcAccountTransactionsDaoI") AccountTransactionsDao accountTransactionsDao) {
        this.accountTransactionsDao = accountTransactionsDao;
    }

    @Override
    public List<AccountTransactions> findByAccountId(Long accountId) throws AccountTransactionNotFound {
        return accountTransactionsDao.findByAccountId(accountId).orElseThrow(() ->
                new AccountTransactionNotFound("There is no transactions for account " + accountId));
    }

    @Override
    public AccountTransactions add(AccountTransactions accountTransactions) {
        return accountTransactionsDao.add(accountTransactions).orElseThrow(RuntimeException::new);
    }

}