package com.revature.ERS.service;

import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.security.auth.login.FailedLoginException;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    UserRepository userRepo;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    AuthenticationService authService;

    UserRole fakeRole = new UserRole();
    User fakeUser = new User();

    @BeforeEach
    public void setup() {
        fakeRole.setId(1);
        fakeRole.setRole("employee");

        fakeUser.setId(1);
        fakeUser.setFirstName("firstName");
        fakeUser.setLastName("lastName");
        fakeUser.setUsername("username");
        fakeUser.setPassword("password");
        fakeUser.setEmail("email");
        fakeUser.setRole(fakeRole);

    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_positive() throws BadParameterException, FailedLoginException {

        when(userRepo.findByUsernameAndPassword("username", "password")).thenReturn(fakeUser);

//        UserDto expected = new UserDto(1, "firstName", "lastName", "email", "username", fakeRole);

        User actual = authService.login("username", "password");

        User expected = fakeUser;

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_invalidEmailAndPassword() throws FailedLoginException {
        Assertions.assertThrows(FailedLoginException.class, () -> {
            authService.login("invalid", "invalid");
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_BadParameter() throws BadParameterException {
        Assertions.assertThrows(BadParameterException.class, () -> {
            authService.login("","");
        });
    }
}
