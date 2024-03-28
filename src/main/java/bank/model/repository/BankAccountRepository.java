package bank.model.repository;

import bank.exceptions.AccountAlreadyExists;
import bank.exceptions.AccountNotFound;
import bank.exceptions.ItemAlreadyExists;
import bank.exceptions.NotFound;
import bank.model.domain.BankAccount;

import java.util.List;

public interface BankAccountRepository extends Repository<BankAccount> {

    @Override
    List<BankAccount> findAll() throws AccountNotFound;

    @Override
    BankAccount findById(Long id) throws AccountNotFound;

    BankAccount find(BankAccount bankAccount) throws AccountNotFound;

    @Override
    BankAccount add(BankAccount bankAccount) throws AccountAlreadyExists;

    @Override
    BankAccount update(BankAccount bankAccount, Long id) throws AccountNotFound;

    @Override
    boolean deleteById(Long id);

}