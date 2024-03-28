package bank.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider extends ConnctionConsts {

    Connection getConnection() throws SQLException;

}
