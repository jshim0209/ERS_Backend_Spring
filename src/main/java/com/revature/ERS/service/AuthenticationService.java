package com.revature.ERS.service;

import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import com.revature.ERS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.security.auth.login.FailedLoginException;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepo;

    public User login(String username, String password) throws FailedLoginException, BadParameterException {

        if (username.trim().equals("") || password.trim().equals("")) {
            throw new BadParameterException("You must provide a username and password to log in");
        }

        User user = userRepo.findByUsernameAndPassword(username.trim(), password.trim());

        if (user == null) {
            throw new FailedLoginException("Invalid username and/or password");
        }

        return user;
    }
}
