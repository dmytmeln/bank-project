package bank.db.dao;

import bank.exceptions.NotFound;
import bank.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<List<T>> findAll();

    Optional<T> findById(Long id);

    Optional<T> add(T t);

    Optional<T> update(T t, Long id);

    boolean deleteById(Long id);

}
