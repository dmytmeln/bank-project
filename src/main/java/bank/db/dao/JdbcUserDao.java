package bank.db.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import bank.db.connection.ConnectionProvider;
import bank.db.mapper.BankMapper;
import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("JdbcUserDao")
public final class JdbcUserDao implements UserDao {

    private final ConnectionProvider connectionProvider;
    private final BankMapper<User> userMapper;

    @Autowired
    public JdbcUserDao(@Qualifier("PooledBankConnectionProvider") ConnectionProvider connectionProvider,
                       @Qualifier("UserMapper") BankMapper<User> userMapper) {
        this.connectionProvider = connectionProvider;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {

        String sqlUserById =
                "SELECT user_id, firstname, lastname, pass, email, phone_number, creation_date " +
                "FROM users " +
                "WHERE user_id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUserById)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return userMapper.mapItem(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<List<User>> findAll() {

        String sqlAllUsers =
                "SELECT user_id, firstname, lastname, pass, email,phone_number, creation_date " +
                "FROM users;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAllUsers)) {

            ResultSet resultSet = statement.executeQuery();
            return userMapper.mapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<List<User>> find(User user) {

        //language=MySQL
        String sqlFindUser =
                """
                SELECT user_id, firstname, lastname, pass, email, phone_number, creation_date
                FROM users
                WHERE email = ? OR phone_number = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPhoneNumber());

            ResultSet resultSet = statement.executeQuery();
            return userMapper.mapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> add(User user) {
        String sqlAddUser =
                "INSERT INTO users (firstname, lastname, pass, email, phone_number, creation_date) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAddUser, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime currentDateTime = LocalDateTime.now();

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setTimestamp(6, Timestamp.valueOf(currentDateTime));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Optional.ofNullable(
                            user.toBuilder()
                                    .id(generatedKeys.getLong(1))
                                    .creationDate(currentDateTime)
                                    .build()
                    );
                } else {
                    throw new SQLException();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> update(User user, Long id) {

        String sqlUpdateTransaction =
                """
                UPDATE users
                SET firstname = ?, lastname = ?, pass = ?, email = ?, phone_number = ?
                WHERE user_id = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdateTransaction)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setLong(6, id);
            statement.executeUpdate();

            return findById(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteById(Long id) {

        String sqlDeleteTransaction =
                """
                DELETE FROM users
                WHERE user_id = ?;
                """;

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