package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.SignUpDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.TokenResponse;
import com.revature.ERS.model.User;
import com.revature.ERS.service.JwtService;
import com.revature.ERS.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    UserDto udto = new UserDto();

    @PostMapping("/signUp")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody SignUpDto signUpDto) throws UserExistsException, JsonProcessingException {
        try {

            User user = userService.createUser(signUpDto);
            String jwt = jwtService.createJwt(user);

            return ResponseEntity.ok().body(new TokenResponse(jwt, user.getId(), user.getUsername(),user.getRole().getRole(), user.getFirstName()));

        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().build();
        }

//        User user = modelMapper.map(signUpDto, User.class);
//
//        UserDto addedUser = userService.createUser(signUpDto);
//
//        String jwt = jwtService.createJwt(user);
//
//        if (addedUser != null) {
//            return ResponseEntity.ok().body(new TokenResponse(jwt, user.getId(), user.getUsername(), user. getRole().getRole(), user.getFirstName()));
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
    }
}
