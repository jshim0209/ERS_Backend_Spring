package com.revature.ers.service;

import com.revature.ers.dto.LoginDto;
import com.revature.ers.exception.BadParameterException;
import com.revature.ers.model.User;
import com.revature.ers.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.security.auth.login.FailedLoginException;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepo;

    public User login(LoginDto loginDto) throws FailedLoginException, BadParameterException {

        if (loginDto.getUsername().trim().equals("") || loginDto.getPassword().trim().equals("")) {
            throw new BadParameterException("You must provide a username and password to log in");
        }

        User user = userRepo.findByUsernameAndPassword(loginDto.getUsername().trim(), loginDto.getPassword().trim());

        if (user == null) {
            throw new FailedLoginException("Invalid username and/or password");
        }

        return user;
    }
}
