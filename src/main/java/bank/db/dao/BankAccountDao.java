package bank.db.dao;

import bank.model.domain.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountDao extends Dao<BankAccount> {

    @Override
    Optional<List<BankAccount>> findAll();

    @Override
    Optional<BankAccount> findById(Long id);

    Optional<BankAccount> find(BankAccount bankAccount);

    @Override
    Optional<BankAccount> add(BankAccount bankAccount);

    @Override
    Optional<BankAccount> update(BankAccount bankAccount, Long id);

    @Override
    boolean deleteById(Long id);

}