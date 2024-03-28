package bank.db.dao;

import bank.db.connection.ConnectionProvider;
import bank.db.mapper.BankMapper;
import bank.model.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JdbcBankAccountDao")
public class JdbcBankAccountDao implements BankAccountDao {

    private final ConnectionProvider connectionProvider;

    private final BankMapper<BankAccount> bankMapper;

    @Autowired
    public JdbcBankAccountDao(@Qualifier("PooledBankConnectionProvider") ConnectionProvider connectionProvider,
                              @Qualifier("BankAccountMapper") BankMapper<BankAccount> bankMapper) {
        this.connectionProvider = connectionProvider;
        this.bankMapper = bankMapper;
    }

    @Override
    public Optional<List<BankAccount>> findAll() {

        String sqlAllAccounts =
                "SELECT bank_account_id, balance, user_id " +
                "FROM bank_accounts;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAllAccounts)) {

            ResultSet resultSet = statement.executeQuery();

            return bankMapper.mapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<BankAccount> findById(Long id) {

        String sqlAccountById =
                "SELECT bank_account_id, balance, user_id " +
                "FROM bank_accounts " +
                "WHERE bank_account_id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAccountById)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return bankMapper.mapItem(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<BankAccount> find(BankAccount bankAccount) {

        //language=MySQL
        String sqlFindAccount =
                """
                SELECT bank_account_id, balance, user_id
                FROM bank_accounts
                WHERE user_id = ?;
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlFindAccount)) {

            statement.setLong(1, bankAccount.getUserId());
            ResultSet resultSet = statement.executeQuery();
            return bankMapper.mapItem(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Optional<BankAccount> add(BankAccount bankAccount) {

        String addAccount =
                "INSERT INTO bank_accounts (user_id) " +
                "VALUES (?)";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(addAccount, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, bankAccount.getUserId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Optional.ofNullable(bankAccount.toBuilder()
                            .id(generatedKeys.getLong(1))
                            .balance(0D)
                            .build());
                }

                return Optional.empty();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<BankAccount> update(BankAccount bankAccount, Long id) {

        String sqlUpdateAccount =
                "UPDATE bank_accounts " +
                "SET balance = ?, user_id = ? " +
                "WHERE bank_account_id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdateAccount)) {

            statement.setDouble(1, bankAccount.getBalance());
            statement.setLong(2, bankAccount.getUserId());
            statement.setLong(3, id);
            statement.executeUpdate();

            return findById(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteById(Long id) {

        String sqlDeleteAccount =
                "DELETE FROM bank_accounts " +
                "WHERE bank_account_id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteAccount)) {

            statement.setLong(1, id);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
