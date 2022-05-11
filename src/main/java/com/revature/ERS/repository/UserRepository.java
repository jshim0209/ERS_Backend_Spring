package com.revature.ERS.repository;

import com.revature.ERS.exception.BadParameterException;
import com.revature.ERS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.FailedLoginException;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public abstract User findByUsernameAndPassword(String username, String password) throws FailedLoginException, BadParameterException;

    public abstract User findByUsernameAndEmail(String username, String email);

}
