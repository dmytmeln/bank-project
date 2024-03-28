package bank.exceptions;

import bank.model.domain.User;

public class UserAlreadyExists extends ItemAlreadyExists {

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(User user) {
        super("Such user already exists " + user);
    }

    public UserAlreadyExists(String message, User user) {
        super(message + " " + user);
    }

}
