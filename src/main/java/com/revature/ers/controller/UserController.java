package com.revature.ers.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ers.dto.SignUpDto;
import com.revature.ers.exception.UserExistsException;
import com.revature.ers.model.TokenResponse;
import com.revature.ers.model.User;
import com.revature.ers.service.JwtService;
import com.revature.ers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${ui.url}", allowCredentials = "true")
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
