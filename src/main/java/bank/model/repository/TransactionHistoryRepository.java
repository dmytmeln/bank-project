package bank.model.repository;

import bank.exceptions.ItemAlreadyExists;
import bank.exceptions.TransactionNotFound;
import bank.model.domain.TransactionHistory;

import java.util.List;

public interface TransactionHistoryRepository extends Repository<TransactionHistory> {

    @Override
    List<TransactionHistory> findAll() throws TransactionNotFound;

    @Override
    TransactionHistory findById(Long id) throws TransactionNotFound;

    @Override
    TransactionHistory add(TransactionHistory transactionHistory);

    @Override
    TransactionHistory update(TransactionHistory transactionHistory, Long id) throws TransactionNotFound;

    @Override
    boolean deleteById(Long id);

}
