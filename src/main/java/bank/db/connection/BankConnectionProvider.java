package bank.db.connection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Qualifier("BankConnectionProvider")
public class BankConnectionProvider implements ConnectionProvider {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_IN, USER, PASS);
    }

}
