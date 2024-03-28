package bank.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BankMapper<T> implements Mapper<T> {

    @Override
    public Optional<T> mapItem(ResultSet resultSet) throws SQLException {
        try (resultSet) {

            if (resultSet.next()) {
                return Optional.of(map(resultSet));
            }

            return Optional.empty();
        }
    }

    public  Optional<List<T>> mapList(ResultSet resultSet) throws SQLException {
        List<T> tList = new ArrayList<>();

        try (resultSet) {
            while (resultSet.next()) {
                tList.add(map(resultSet));
            }
        }

        return tList.isEmpty() ? Optional.empty() : Optional.of(tList);
    }

    abstract T map(ResultSet resultSet) throws SQLException;

}
