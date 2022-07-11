package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.LoginDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.TokenResponse;
import com.revature.ERS.model.User;
import com.revature.ERS.service.AuthenticationService;
import com.revature.ERS.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtService jwtService;

    UserDto udto = new UserDto();

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDto dto) throws JsonProcessingException {

            try {
                User user = authService.login(dto);

                String jwt = jwtService.createJwt(user);

                return ResponseEntity.ok().body(new TokenResponse(jwt, user.getId(), user.getUsername(), user.getRole().getRole(), user.getFirstName()));
            } catch (FailedLoginException | BadParameterException e) {
                return ResponseEntity.badRequest().build();
            }
    }
}

