package bank.db.mapper;

import bank.model.domain.AccountTransactions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Qualifier("AccountTransactionsMapper")
public class AccountTransactionsMapper extends BankMapper<AccountTransactions> {

    @Override
    AccountTransactions map(ResultSet resultSet) throws SQLException {
        return AccountTransactions.builder()
                .accountId(resultSet.getLong("bank_account_id"))
                .transactionId(resultSet.getLong("transaction_history_id"))
                .build();
    }

}
