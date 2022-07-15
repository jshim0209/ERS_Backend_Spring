package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.SignUpDto;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.TokenResponse;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.service.JwtService;
import com.revature.ERS.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    JwtService jwtService;

    @InjectMocks
    UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private User user;
    private UserRole userRole;
    private SignUpDto signUpDto;
    private TokenResponse tokenResponse;
    private String jsonSignUpDto;
    private String jsonTokenResponse;
    private User userExisted;
    private SignUpDto invalidUsername;
    private SignUpDto invalidEmail;

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController).addPlaceholderValue("ui.url", "http://localhost:4200").build();
    }

    @BeforeEach
    void init() throws JsonProcessingException {
        userRole = new UserRole(1, "employee");
        user = new User(2, "Jiwon", "Shim", "jshim", "password", "jshim@email.com", userRole);
        signUpDto = new SignUpDto("Jiwon", "Shim", "jshim", "password", "jshim@email.com", userRole);
//        userExisted = new User(1, "Minah", "Kim", "mkim", "password", "mkim@email.com", userRole);
//        invalidUsername = new SignUpDto("Jiwon", "Shim", "mkim", "password", "jshim@email.com", userRole);
//        invalidEmail = new SignUpDto("Jiwon", "Shim", "jshim", "password", "mkim@email.com", userRole);
        jsonSignUpDto = mapper.writeValueAsString(signUpDto);

    }

    @Test
    void registerUser_positive() throws Exception {
        when(userService.createUser(signUpDto)).thenReturn(user);
        String jwt = jwtService.createJwt(user);

        tokenResponse = new TokenResponse(jwt, user.getId(), user.getUsername(), user.getRole().getRole(), user.getFirstName());
        jsonTokenResponse = mapper.writeValueAsString(tokenResponse);

        this.mockMvc.perform(post("/signUp")
                .content(jsonSignUpDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonTokenResponse))
                .andExpect(status().isOk());
    }

    @Test
    void userExistException() throws Exception {
        when(userService.createUser(signUpDto)).thenThrow(UserExistsException.class);

        mockMvc.perform(post("/signUp")
                .content(jsonSignUpDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
