package bank.db.dao;

import bank.exceptions.UserNotFound;
import bank.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    @Override
    Optional<List<User>> findAll();

    @Override
    Optional<User> findById(Long id);

    Optional<List<User>> find(User user);

    @Override
    Optional<User> add(User user);

    @Override
    Optional<User> update(User user, Long id);

    @Override
    boolean deleteById(Long id);

}
