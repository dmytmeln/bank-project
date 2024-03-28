package bank.db.dao;

import bank.db.connection.ConnectionProvider;
import bank.model.domain.TransactionHistory;
import bank.db.mapper.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JdbcTransactionHistoryDao")
public class JdbcTransactionHistoryDao implements TransactionHistoryDao {

    private final ConnectionProvider connectionProvider;

    private final BankMapper<TransactionHistory> transactionMapper;

    @Autowired
    public JdbcTransactionHistoryDao(@Qualifier("PooledBankConnectionProvider") ConnectionProvider connectionProvider,
                                     @Qualifier("TransactionHistoryMapper") BankMapper<TransactionHistory> transactionMapper) {
        this.connectionProvider = connectionProvider;
        this.transactionMapper = transactionMapper;
    }

    public Optional<TransactionHistory> findById(Long bankAccountId, Long transactionId) {

        String sqlTransactionByIds =
                "SELECT th.transaction_history_id, th.msg, th.transaction_type, th.money_amount, th.transaction_date " +
                "FROM bank_transactions bt " +
                "JOIN transaction_histories th ON bt.transaction_history_id = th.transaction_history_id " +
                "WHERE bt.bank_account_id = ? AND bt.transaction_history_id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlTransactionByIds)) {

            statement.setLong(1, bankAccountId);
            statement.setLong(2, transactionId);
            ResultSet resultSet = statement.executeQuery();
            return transactionMapper.mapItem(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<TransactionHistory> findById(Long id) {

        String sqlTransactionById =
                "SELECT transaction_history_id, msg, transaction_type, money_amount, transaction_date " +
                "FROM transaction_histories " +
                "WHERE transaction_history_id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlTransactionById)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return transactionMapper.mapItem(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<List<TransactionHistory>> findAll() {

        String sqlAllTransactions =
                "SELECT transaction_history_id, msg, transaction_type, money_amount, transaction_date " +
                "FROM transaction_histories;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAllTransactions)) {

            ResultSet resultSet = statement.executeQuery();
            return transactionMapper.mapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<List<TransactionHistory>> findAll(Long bankAccountId) {

        String sqlAllAccountTransaction =
                "SELECT th.transaction_history_id, th.msg, th.transaction_type, th.money_amount, th.transaction_date " +
                "FROM bank_transactions bt " +
                "JOIN transaction_histories th ON bt.transaction_history_id = th.transaction_history_id " +
                "WHERE bt.bank_account_id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAllAccountTransaction)) {

            statement.setLong(1, bankAccountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Optional<List<TransactionHistory>> transactions = transactionMapper.mapList(resultSet);
                if (transactions.isEmpty()) {
                    throw new SQLException();
                }
                return transactions;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<TransactionHistory> add(TransactionHistory transactionHistory) {

        String sqlAddTransaction =
                "INSERT INTO transaction_histories (msg, transaction_type, money_amount, transaction_date) " +
                "VALUES (?, ?, ?, ?);";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAddTransaction, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime currentDateTime = LocalDateTime.now();

            statement.setString(1, transactionHistory.getMsg());
            statement.setString(2, transactionHistory.getTransactionType());
            statement.setDouble(3, transactionHistory.getMoneyAmount());
            statement.setTimestamp(4, Timestamp.valueOf(currentDateTime));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();

                return Optional.ofNullable(transactionHistory.toBuilder()
                        .id(generatedKeys.getLong(1))
                        .transactionDate(currentDateTime)
                        .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<TransactionHistory> update(TransactionHistory transactionHistory, Long id) {

        String sqlUpdateTransaction =
                "UPDATE transaction_histories " +
                "SET msg = ?, transaction_type = ?, money_amount = ? " +
                "WHERE transaction_history_id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdateTransaction)) {

            statement.setString(1, transactionHistory.getMsg());
            statement.setString(2, transactionHistory.getTransactionType());
            statement.setDouble(3, transactionHistory.getMoneyAmount());
            statement.setLong(4, id);
            statement.executeUpdate();

            return findById(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteById(Long id) {

        String sqlDeleteTransaction =
                "DELETE FROM transaction_histories " +
                "WHERE transaction_history_id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteTransaction)) {

            statement.setLong(1, id);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}