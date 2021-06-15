package com.endava.courseschedulingapp.services;

import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.entities.UserRole;
import com.endava.courseschedulingapp.exceptions.NotFoundException;
import com.endava.courseschedulingapp.exceptions.ValidationModelException;
import com.endava.courseschedulingapp.repositories.UserRepository;
import com.endava.courseschedulingapp.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @Description("Should return the user by ID")
    void shouldReturnUser_ifExists_true() {

        User userMock = User.builder().id(10L).firstName("Test").lastName("User")
                .enabled(true).expired(false).locked(false)
                .credentialExpired(false).email("test.user@yahoo.com")
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.of(userMock));

        User userById = userService.getUserById(userMock.getId());

        assertNotNull(userById);
        assertEquals(userMock, userById);
    }

    @Test
    @Description("Should create a new user")
    void shouldCreateUser_true() {
        User userMock = User.builder()
                .id(3L)
                .username("LoreClient4")
                .firstName("Test")
                .lastName("User")
                .email("test.user@yahoo.com")
                .password("1234567")
                .build();

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(userMock);

        User client = userService.createClient(userMock);

        assertNotNull(client);
        assertEquals(UserRole.CLIENT.toDBRole(), client.getRole());
        assertTrue(client.isEnabled());
        assertFalse(client.isExpired());
        assertFalse(client.isLocked());
        assertFalse(client.isCredentialExpired());
    }

    @Test
    @Description("Throw exception if username already exists when creating a new user")
    void createUser_whenExists_throwException() {
        User userMock = User.builder()
                .id(4L)
                .username("LoreClient4")
                .firstName("Test")
                .lastName("User")
                .email("test.user@yahoo.com")
                .password("1234567")
                .build();

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(userMock));

        assertThrows(ValidationModelException.class, () -> userService.createClient(userMock));
    }

    @Test
    @Description("Throw exception if password has less than 6 characters")
    void createUser_whenPasswordNotValid_throwException() {
        User userMock = User.builder()
                .id(5L)
                .username("LoreClient4")
                .firstName("Test")
                .lastName("User")
                .email("test.user@yahoo.com")
                .password("1234")
                .build();

        when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());

        assertThrows(ValidationModelException.class, () -> userService.createClient(userMock));
    }

    @Test
    @Description("Throw exception if the user does not exist")
    void updateUser_whenNotExists_throwException() {
        User userMock = User.builder()
                .id(4L)
                .firstName("Test")
                .lastName("User")
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userMock));
    }
}