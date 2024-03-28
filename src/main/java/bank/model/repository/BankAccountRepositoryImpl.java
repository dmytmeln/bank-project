package bank.model.repository;

import bank.db.dao.BankAccountDao;
import bank.exceptions.*;
import bank.model.domain.BankAccount;
import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("BankAccountRepositoryImpl")
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final BankAccountDao bankAccountDao;
    private final UserRepository userRepo;

    @Autowired
    public BankAccountRepositoryImpl(@Qualifier("JdbcBankAccountDao") BankAccountDao bankAccountDao,
                                     @Qualifier("UserRepositoryImpl") UserRepository userRepo) {
        this.bankAccountDao = bankAccountDao;
        this.userRepo = userRepo;
    }

    @Override
    public List<BankAccount> findAll() throws AccountNotFound {
        return bankAccountDao.findAll().orElseThrow(() -> new AccountNotFound("There are no users"));
    }

    @Override
    public BankAccount findById(Long id) throws AccountNotFound {
        return bankAccountDao.findById(id).orElseThrow(AccountNotFound::new);
    }

    @Override
    public BankAccount find(BankAccount bankAccount) throws AccountNotFound {
        return bankAccountDao.find(bankAccount).orElseThrow(() -> new AccountNotFound(bankAccount));
    }

    @Override
    public BankAccount add(BankAccount bankAccount) throws AccountAlreadyExists {
        try {

            bankAccount = find(bankAccount);
            User user = userRepo.findById(bankAccount.getId());
            throw  new AccountAlreadyExists(
                    String.format("Account with user firstname: %s, lastname: %s, email: %s, phone number %s already exists",
                            user.getFirstName(), user.getLastName(),user.getEmail(), user.getPhoneNumber())
            );

        } catch (AccountNotFound e) {
            return bankAccountDao.add(bankAccount).orElseThrow(RuntimeException::new);
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BankAccount update(BankAccount bankAccount, Long id) throws AccountNotFound {
        return bankAccountDao.update(bankAccount, id).orElseThrow(() -> new AccountNotFound(bankAccount));
    }

    @Override
    public boolean deleteById(Long id) {
        return bankAccountDao.deleteById(id);
    }

}