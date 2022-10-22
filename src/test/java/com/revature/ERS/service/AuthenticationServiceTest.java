package com.revature.ERS.service;

import com.revature.ERS.dto.LoginDto;
import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.dao.UserRepository;
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
class AuthenticationServiceTest {

    @Mock
    UserRepository userRepo;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    AuthenticationService authService;

    UserRole fakeRole = new UserRole();
    User fakeUser = new User();
    LoginDto loginDto = new LoginDto();
    LoginDto invalidUsername = new LoginDto();
    LoginDto invalidPassword = new LoginDto();
    LoginDto blankLoginDto = new LoginDto();

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

        loginDto.setUsername("username");
        loginDto.setPassword("password");

        invalidUsername.setUsername("invalid");
        invalidUsername.setPassword("password");

        invalidPassword.setUsername("username");
        invalidPassword.setPassword("invalid");

        blankLoginDto.setUsername("");
        blankLoginDto.setPassword("");
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_positive() throws BadParameterException, FailedLoginException {

        when(userRepo.findByUsernameAndPassword("username", "password")).thenReturn(fakeUser);

        User actual = authService.login(loginDto);

        User expected = fakeUser;

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_invalidUsername() {
        Assertions.assertThrows(FailedLoginException.class, () -> {
            authService.login(invalidUsername);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_invalidPassword() {
        Assertions.assertThrows(FailedLoginException.class, () -> {
            authService.login(invalidPassword);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void test_login_BadParameter() {
        Assertions.assertThrows(BadParameterException.class, () -> {
            authService.login(blankLoginDto);
        });
    }
}
