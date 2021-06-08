package com.endava.courseschedulingapp.services;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.repositories.UserRepository;
import com.endava.courseschedulingapp.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @Description("Should return the id of the course")
    void shouldReturnACourseById() {
    }
}