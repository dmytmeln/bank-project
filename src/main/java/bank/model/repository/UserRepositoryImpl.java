package bank.model.repository;

import bank.db.dao.UserDao;
import bank.exceptions.UserAlreadyExists;
import bank.exceptions.UserNotFound;
import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("UserRepositoryImpl")
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Autowired
    public UserRepositoryImpl(@Qualifier("JdbcUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() throws UserNotFound {
        return userDao.findAll().orElseThrow(() -> new UserNotFound("There are no users"));
    }

    @Override
    public User findById(Long id) throws UserNotFound {
        return userDao.findById(id).orElseThrow(UserNotFound::new);
    }

    @Override
    public List<User> find(User user) throws UserNotFound {
        return userDao.find(user).orElseThrow(() -> new UserNotFound(user));
    }

    @Override
    public User add(User user) throws UserAlreadyExists {
        try {
            // if user already exists -> we can't add him, so throw exception UserAlreadyExists
            find(user);
            throw new UserAlreadyExists(
                    String.format("User with email: %s and/or phone number: %s already exists ", user.getEmail(), user.getPhoneNumber())
            );

        } catch (UserNotFound e) {
            // if it doesn't exist -> we can add him
            return userDao.add(user).orElseThrow(RuntimeException::new);
        }
    }

    @Override
    public User update(User user, Long id) throws UserNotFound {
        return userDao.update(user, id).orElseThrow(() -> new UserNotFound(user));
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

}