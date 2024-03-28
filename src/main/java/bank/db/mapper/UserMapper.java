package bank.db.mapper;

import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Qualifier("UserMapper")
public final class UserMapper extends BankMapper<User> {

    @Override
    User map(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .firstName(resultSet.getString("firstname"))
                .lastName(resultSet.getString("lastname"))
                .password(resultSet.getString("pass"))
                .email(resultSet.getString("email"))
                .phoneNumber(resultSet.getString("phone_number"))
                .creationDate(resultSet.getTimestamp("creation_date").toLocalDateTime())
                .build();
    }

}
