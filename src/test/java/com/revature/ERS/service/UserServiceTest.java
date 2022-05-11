package com.revature.ERS.service;

import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.NotFound;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepo;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    UserService userService;

    UserRole fakeUserRole1 = new UserRole();
    UserRole fakeUserRole2 = new UserRole();
    User fakeUser1 = new User();
    User fakeUser2 = new User();

    @BeforeEach
    public void setup() {
        fakeUserRole1.setId(1);
        fakeUserRole1.setRole("employee");

        fakeUserRole2.setId(2);
        fakeUserRole2.setRole("manager");

        fakeUser1.setId(1);
        fakeUser1.setFirstName("firstName1");
        fakeUser1.setLastName("lastName1");
        fakeUser1.setUsername("username1");
        fakeUser1.setPassword("password1");
        fakeUser1.setEmail("email1");
        fakeUser1.setRole(fakeUserRole1);

        fakeUser2.setId(2);
        fakeUser2.setFirstName("firstName2");
        fakeUser2.setLastName("lastName2");
        fakeUser2.setUsername("username2");
        fakeUser2.setPassword("password2");
        fakeUser2.setEmail("email2");
        fakeUser2.setRole(fakeUserRole2);

    }


    @Test
    void test_get_all_users_positive() {

        List<User> fakeUsers = new ArrayList<>();
        fakeUsers.add(fakeUser1);
        fakeUsers.add(fakeUser2);

        when(userRepo.findAll()).thenReturn(fakeUsers);

        List<UserDto> expected = new ArrayList<>();
        expected.add(new UserDto(1, "firstName1", "lastName1", "email1", "username1", fakeUserRole1));
        expected.add(new UserDto(2, "firstName2", "lastName2", "email2", "username2", fakeUserRole2));

        List<UserDto> actual = userService.getAllUsers();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void test_get_userById_positive() throws NotFound {

        when(userRepo.findById(1)).thenReturn(Optional.of(fakeUser1));

        UserDto expected = new UserDto(1, "firstName1", "lastName1", "email1", "username1", fakeUserRole1);

        UserDto actual = userService.getUserById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_register_user_positive() throws UserExistsException {

        User addedUser = new User();
        addedUser.setId(3);
        addedUser.setFirstName("firstName3");
        addedUser.setLastName("lastName3");
        addedUser.setUsername("username3");
        addedUser.setPassword("password3");
        addedUser.setEmail("email3");
        addedUser.setRole(fakeUserRole1);

        when(userRepo.findByUsernameAndEmail(addedUser.getUsername(), addedUser.getEmail())).thenReturn(null);
        when(userRepo.save(addedUser)).thenReturn(addedUser);

        UserDto expected = new UserDto();
        expected.setId(3);
        expected.setFirstName("firstName3");
        expected.setLastName("lastName3");
        expected.setUsername("username3");
        expected.setEmail("email3");
        expected.setUserRole(fakeUserRole1);

        UserDto actual = userService.createUser(addedUser);
        Assertions.assertEquals(expected, actual);
    }
}