package com.example.engine.persistence;

import com.example.engine.exception.UserAlreadyExistsException;
import com.example.engine.exception.UserNotFoundException;
import com.example.engine.persistence.model.User;
import com.example.engine.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUser(User user) {
        if (userRepository.existsById(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(UserRole.ROLE_USER);
        return userRepository.save(newUser);
    }

    public User get(String email) {
        return userRepository
                .findById(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
