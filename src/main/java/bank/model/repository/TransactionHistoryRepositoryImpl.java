package bank.model.repository;

import bank.db.dao.TransactionHistoryDao;
import bank.exceptions.TransactionNotFound;
import bank.model.domain.TransactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("TransactionHistoryRepositoryImpl")
public class TransactionHistoryRepositoryImpl implements TransactionHistoryRepository {

    private final TransactionHistoryDao transactionHistoryDao;

    @Autowired
    public TransactionHistoryRepositoryImpl(@Qualifier("JdbcTransactionHistoryDao") TransactionHistoryDao transactionHistoryDao) {
        this.transactionHistoryDao = transactionHistoryDao;
    }

    public List<TransactionHistory> findAll() throws TransactionNotFound {
        return transactionHistoryDao.findAll().orElseThrow(() -> new TransactionNotFound("There are no transactions"));
    }

    @Override
    public TransactionHistory findById(Long id) throws TransactionNotFound {
        return transactionHistoryDao.findById(id).orElseThrow(TransactionNotFound::new);
    }

    @Override
    public TransactionHistory add(TransactionHistory transactionHistory) {
        return transactionHistoryDao.add(transactionHistory).orElseThrow(RuntimeException::new);
    }

    @Override
    public TransactionHistory update(TransactionHistory transactionHistory, Long id) throws TransactionNotFound {
        return transactionHistoryDao.update(transactionHistory, id)
                .orElseThrow(() -> new TransactionNotFound(transactionHistory));
    }

    @Override
    public boolean deleteById(Long id) {
        return transactionHistoryDao.deleteById(id);
    }

}
