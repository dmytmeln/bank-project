package bank.db.mapper;

import bank.model.domain.TransactionHistory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Qualifier("TransactionHistoryMapper")
public class TransactionHistoryMapper extends BankMapper<TransactionHistory> {

    @Override
    TransactionHistory map(ResultSet resultSet) throws SQLException {
        return TransactionHistory.builder()
                .id(resultSet.getLong("transaction_history_id"))
                .msg(resultSet.getString("msg"))
                .transactionType(resultSet.getString("transaction_type"))
                .moneyAmount(resultSet.getDouble("money_amount"))
                .transactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime())
                .build();
    }

}