package bank.model.repository;

import bank.exceptions.UserAlreadyExists;
import bank.exceptions.UserNotFound;
import bank.model.domain.User;
import java.util.List;


public interface UserRepository extends Repository<User> {

    @Override
    List<User> findAll() throws UserNotFound;

    @Override
    User findById(Long id) throws UserNotFound;

    List<User> find(User user) throws UserNotFound;

    @Override
    User add(User user) throws UserAlreadyExists;

    @Override
    User update(User user, Long id) throws UserNotFound;

    @Override
    boolean deleteById(Long id);

}
