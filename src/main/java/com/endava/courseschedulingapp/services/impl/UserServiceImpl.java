package com.endava.courseschedulingapp.services.impl;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.entities.UserRole;
import com.endava.courseschedulingapp.exceptions.NotFoundException;
import com.endava.courseschedulingapp.exceptions.ValidationModelException;
import com.endava.courseschedulingapp.repositories.UserRepository;
import com.endava.courseschedulingapp.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private User create(User user) {
        validate(user);
        user.setEnabled(true);
        user.setCredentialExpired(false);
        user.setLocked(false);
        user.setExpired(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User createClient(User user) {
        user.setRole(UserRole.CLIENT.toDBRole());
        return create(user);
    }

    @Override
    public User createTrainer(User user) {
        user.setRole(UserRole.TRAINER.toDBRole());
        return create(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User update(Long userId) {
        return null;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        User currentUser = getUserById(userId);
        userRepository.delete(currentUser);
    }

    private void validate(User user) {
        //validate username duplicate
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ValidationModelException(String.format("An user with the Username '%s' already exists.", user.getUsername()));
        }

        if(user.getPassword() != null && user.getPassword().length() < 6) {
            throw new ValidationModelException("The password must have a length of minimum 6 characters");
        }
    }
}
