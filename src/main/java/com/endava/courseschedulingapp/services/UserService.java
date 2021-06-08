package com.endava.courseschedulingapp.services;

import com.endava.courseschedulingapp.entities.User;

import java.util.Optional;

public interface UserService {

    User createClient(User user);

    User createTrainer(User user);

    User getUserById(Long userId);
    User update(Long userId);

    Optional<User> findUserByUsername(String username);

    void deleteUser(Long userId);
}