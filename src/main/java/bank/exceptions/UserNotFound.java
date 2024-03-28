package bank.exceptions;

import bank.model.domain.User;

public class UserNotFound extends NotFound {

    public UserNotFound() {
        this("User doesn't exist");
    }

    public UserNotFound(String message) {
        super(message);
    }

    public UserNotFound(User user) {
        super("Such user doesn't exist " + user);
    }

}