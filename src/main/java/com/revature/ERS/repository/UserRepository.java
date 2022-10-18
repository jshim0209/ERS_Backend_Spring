package com.revature.ERS.repository;

import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.FailedLoginException;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password) throws FailedLoginException, BadParameterException;

    User findByUsername(String username);

    User findByEmail(String email);

}
