//package com.revature.ERS.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.ERS.dto.LoginDto;
//import com.revature.ERS.exception.BadParameterException;
//import com.revature.ERS.model.TokenResponse;
//import com.revature.ERS.model.User;
//import com.revature.ERS.model.UserRole;
//import com.revature.ERS.service.AuthenticationService;
//import com.revature.ERS.service.JwtService;
//import jdk.internal.net.http.common.Log;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.security.auth.login.FailedLoginException;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class AuthenticationControllerTest {
//
//    @Mock
//    AuthenticationService authService;
//
//    @Mock
//    JwtService jwtService;
//
//    @InjectMocks
//    AuthenticationController authController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    private static LoginDto loginDto;
//    private static UserRole userRole;
//    private static User user;
//
////    LoginDto loginDto = new LoginDto();
////    User user = new User();
////    UserRole employee = new UserRole();
//
//
//
//    @BeforeAll
//    void init() {
//
//        loginDto = new LoginDto("username", "password");
//        userRole = new UserRole(1, "employee");
//        user = new User(1, "firstName", "lastName", "username", "password", "username@email.com", userRole);
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//
////        loginDto.setUsername("username");
////        loginDto.setPassword("password");
//
////        employee.setId(1);
////        employee.setRole("employee");
//
////        user.setId(1);
////        user.setFirstName("firstName");
////        user.setLastName("lastName");
////        user.setEmail("username@email.com");
////        user.setUsername("username");
////        user.setPassword("password");
////        user.setRole(employee);
//
//    }
//
//    @Test
//    void login_positive() throws Exception {
//
//        System.out.println(loginDto);
//        System.out.println(user);
//
//        when(authService.login(loginDto)).thenReturn(user);
//
//        String jwt = jwtService.createJwt(user);
//
//        TokenResponse tokenResponse = new TokenResponse(jwt, user.getId(), user.getUsername(), user.getRole().getRole(), user.getFirstName());
//
//        mockMvc.perform(post("/login"))
//                .andExpect(content().json(mapper.writeValueAsString(tokenResponse)))
//                .andExpect(status().isOk());
//    }
//}
