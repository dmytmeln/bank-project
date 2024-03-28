package bank.db.mapper;

import bank.model.domain.BankAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Qualifier("BankAccountMapper")
public class BankAccountMapper extends BankMapper<BankAccount> {

    @Override
    BankAccount map(ResultSet resultSet) throws SQLException {
        return BankAccount.builder()
                .id(resultSet.getLong("bank_account_id"))
                .balance(resultSet.getDouble("balance"))
                .userId(resultSet.getLong("user_id"))
                .build();
    }

}
