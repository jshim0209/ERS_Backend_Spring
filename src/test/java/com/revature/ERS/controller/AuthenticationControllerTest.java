package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.LoginDto;
import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.TokenResponse;
import com.revature.ERS.model.User;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.service.AuthenticationService;
import com.revature.ERS.service.JwtService;
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

import javax.security.auth.login.FailedLoginException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest {

    @Mock
    AuthenticationService authService;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthenticationController authController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private LoginDto loginDto;
    private UserRole userRole;
    private User user;
    private TokenResponse tokenResponse;
    private String jsonLoginDto;
    private String jsonTokenResponse;

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.authController).addPlaceholderValue("ui.url", "http://localhost:4200").build();
    }

    @BeforeEach
    void init() throws JsonProcessingException {
        loginDto = new LoginDto("jshim", "jiwon1234");
        userRole = new UserRole(1, "employee");
        user = new User(1, "Jiwon", "Shim", "jshim", "jiwon1234", "jshim@email.com", userRole);
        jsonLoginDto = mapper.writeValueAsString(loginDto);
    }

    @Test
    void login_positive() throws Exception {
        when(authService.login(loginDto)).thenReturn(user);
        String jwt = jwtService.createJwt(user);

        tokenResponse = new TokenResponse(jwt, user.getId(), user.getUsername(), user.getRole().getRole(), user.getFirstName());
        jsonTokenResponse = mapper.writeValueAsString(tokenResponse);

        this.mockMvc.perform(post("/login")
                        .content(jsonLoginDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonTokenResponse))
                .andExpect(status().isOk());
    }

    @Test
    void invalidUsername_or_password() throws Exception {
        when(authService.login(loginDto)).thenThrow(FailedLoginException.class);

        this.mockMvc.perform(post("/login")
                .content(jsonLoginDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankUsername_or_password() throws Exception {
        when(authService.login(loginDto)).thenThrow(BadParameterException.class);

        this.mockMvc.perform(post("/login")
                .content(jsonLoginDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}