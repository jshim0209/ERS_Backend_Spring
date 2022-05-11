package com.revature.ERS.controller;

import com.revature.ERS.dto.SignUpDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.User;
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
    private ModelMapper modelMapper;

    UserDto udto = new UserDto();

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) throws UserExistsException {
        User user = modelMapper.map(signUpDto, User.class);

        UserDto addedUser = userService.createUser(user);

        if (addedUser != null) {
            return ResponseEntity.status(200).body(addedUser);
        } else {
            return ResponseEntity.status(400).body("Registration Failed");
        }
    }
}
