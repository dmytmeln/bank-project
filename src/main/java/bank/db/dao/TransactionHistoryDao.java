package bank.db.dao;

import bank.exceptions.TransactionNotFound;
import bank.model.domain.TransactionHistory;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryDao extends Dao<TransactionHistory> {

    @Override
    Optional<List<TransactionHistory>> findAll();

    @Override
    Optional<TransactionHistory> findById(Long id);

    @Override
    Optional<TransactionHistory> add(TransactionHistory transactionHistory);

    @Override
    Optional<TransactionHistory> update(TransactionHistory transactionHistory, Long id);

    @Override
    boolean deleteById(Long id);

}
