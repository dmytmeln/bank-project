package bank.db.dao;

import bank.db.connection.ConnectionProvider;
import bank.db.mapper.BankMapper;
import bank.model.domain.AccountTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JdbcAccountTransactionsDaoI")
public class JdbcAccountTransactionsDaoI implements AccountTransactionsDao {

    private final ConnectionProvider connectionProvider;

    private final BankMapper<AccountTransactions> mapper;

    @Autowired
    public JdbcAccountTransactionsDaoI(@Qualifier("PooledBankConnectionProvider") ConnectionProvider connectionProvider,
                                       @Qualifier("AccountTransactionsMapper") BankMapper<AccountTransactions> mapper) {
        this.connectionProvider = connectionProvider;
        this.mapper = mapper;
    }

    @Override
    public Optional<List<AccountTransactions>> findByAccountId(Long accountId) {

        //language=MySQL
        String sqlGetById =
                """
                SELECT bank_account_id, transaction_history_id
                FROM bank_transactions
                WHERE bank_account_id = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlGetById)) {

            statement.setLong(1, accountId);

            ResultSet resultSet = statement.executeQuery();

            return mapper.mapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<AccountTransactions> add(AccountTransactions accountTransactions) {

        //language=MySQL
        String addAccountTransaction =
                """
                INSERT INTO bank_transactions\s
                VALUES (?, ?);
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(addAccountTransaction)) {

            statement.setLong(1, accountTransactions.getAccountId());
            statement.setLong(2, accountTransactions.getTransactionId());

            statement.executeUpdate();

            return Optional.of(accountTransactions);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
