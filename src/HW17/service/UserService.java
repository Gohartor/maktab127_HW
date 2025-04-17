package HW17.service;

import HW17.config.ApplicationContext;
import HW17.entity.User;
import HW17.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = ApplicationContext.getInstance().getUserRepository();
    }

    public User registerUser(String username, String password) {
        if (usernameExists(username)) {
            throw new RuntimeException("username is already exists.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        return userRepository.create(newUser);
    }

    public User loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("username or password is incorrect.");
        }
    }

    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}