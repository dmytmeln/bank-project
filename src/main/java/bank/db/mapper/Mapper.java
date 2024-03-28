package bank.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

interface Mapper<T> {

    Optional<T> mapItem(ResultSet resultSet) throws SQLException;

    Optional<List<T>> mapList(ResultSet resultSet) throws SQLException;

}