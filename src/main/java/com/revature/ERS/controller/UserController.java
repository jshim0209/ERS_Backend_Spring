package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.SignUpDto;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.TokenResponse;
import com.revature.ERS.model.User;
import com.revature.ERS.service.JwtService;
import com.revature.ERS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/signUp")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody SignUpDto signUpDto) throws JsonProcessingException {
        try {

            User user = userService.createUser(signUpDto);
            String jwt = jwtService.createJwt(user);

            return ResponseEntity.ok().body(new TokenResponse(jwt, user.getId(), user.getUsername(),user.getRole().getRole(), user.getFirstName()));

        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
