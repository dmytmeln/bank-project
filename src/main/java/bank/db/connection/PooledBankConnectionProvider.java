package bank.db.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Qualifier("PooledBankConnectionProvider")
public class PooledBankConnectionProvider implements ConnectionProvider {

    private final DataSource dataSource;

    public PooledBankConnectionProvider() {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(URL_IN);
        hikariConfig.setUsername(USER);
        hikariConfig.setPassword(PASS);

        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(1500);

        dataSource = new HikariDataSource(hikariConfig);

    }

    @Override
    public Connection getConnection() throws SQLException {
        return  dataSource.getConnection();
    }
}
