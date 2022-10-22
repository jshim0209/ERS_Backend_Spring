package com.revature.ers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ers.model.User;
import com.revature.ers.model.UserRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static JwtService jwtService;
    private static User user;
    private static UserRole employee;
    private static String jwt;

    @BeforeAll
    static void init() {
        jwtService = new JwtService();
        employee = new UserRole(1, "employee");
        user = new User(1, "Jiwon", "Shim", "jshim", "password", "jshim@email.com", employee);
    }

    @Test
    void createJwt_positive() throws JsonProcessingException {
        jwt = jwtService.createJwt(user);
        Assertions.assertNotNull(jwt);
    }

//    @Test
//    void parseJwt_positive() throws JsonProcessingException {
//        String jwt = jwtService.createJwt(user);
//
//        System.out.println(jwt);
//
//        Boolean actual = jwtService.parseJwt(jwt);
//
//        Assertions.assertTrue(actual);
//    }

    @Test
    void test_checkSetExpiration_positive() throws JsonProcessingException {
        String jwt = jwtService.createJwt(user);
        Date newDate = Date.from(Instant.now().plus(11, ChronoUnit.DAYS));
        String newJwt = jwtService.setExpiration(jwt, newDate);
        Assertions.assertNotEquals(jwt, newJwt);
    }

}
