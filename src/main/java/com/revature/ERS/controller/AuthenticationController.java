package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.LoginDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import com.revature.ERS.service.AuthenticationService;
import com.revature.ERS.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;

@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "*", allowedHeaders = "*")
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtService jwtService;

    UserDto udto = new UserDto();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) throws JsonProcessingException {
        try {
            User user = authService.login(dto.getUsername(), dto.getPassword());

            String jwt = jwtService.createJwt(user);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token", jwt);

            udto.setId(user.getId());
            udto.setFirstName(user.getFirstName());
            udto.setLastName(user.getLastName());
            udto.setEmail(user.getEmail());
            udto.setUsername(user.getUsername());
            udto.setUserRole(user.getRole().getRole());

            return ResponseEntity.ok().headers(responseHeaders).body(udto);
        } catch (FailedLoginException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (BadParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
