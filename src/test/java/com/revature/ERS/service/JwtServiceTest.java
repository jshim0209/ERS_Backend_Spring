package com.revature.ERS.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static JwtService jwtService;
    private static User user;
    private static UserRole employee;

    @BeforeAll
    static void init() {
        jwtService = new JwtService();
        employee = new UserRole(1, "employee");
        user = new User(1, "Jiwon", "Shim", "jshim", "password", "jshim@email.com", employee);
    }

    @Test
    void createJwt_positive() throws JsonProcessingException {
        String jwt = jwtService.createJwt(user);
        Assertions.assertNotNull(jwt);
    }
}
