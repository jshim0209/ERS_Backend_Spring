package com.revature.ERS.service;

import com.revature.ERS.dto.SignUpDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.exception.UserExistsException;
import com.revature.ERS.model.User;
import com.revature.ERS.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {

        List<UserDto> userDtos = new ArrayList<>();

        List<User> users = userRepo.findAll();
        for (User u : users) {
            userDtos.add(modelMapper.map(u, UserDto.class));
        }
        return userDtos;
    }

    public User getUserById(int id) {
        Optional<User> optional = userRepo.findById(id);

        if(optional.isPresent()) {
            return modelMapper.map(optional, User.class);
        }
        return null;
    }

    public User createUser(SignUpDto signUpDto) throws UserExistsException {

        User userAdded = new User(0, signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getUsername(), signUpDto.getPassword(), signUpDto.getEmail(), signUpDto.getUserRole());

        User checkUserByUseranme = userRepo.findByUsername(signUpDto.getUsername());
        User checkUserByEmail = userRepo.findByEmail(signUpDto.getEmail());

        if (checkUserByUseranme != null) {
            throw new UserExistsException("An account exists with username of " + signUpDto.getUsername());
        }
        else if (checkUserByEmail != null) {
            throw new UserExistsException("An account exists with email of " + signUpDto.getEmail());
        } else {
            return userRepo.save(userAdded);
        }
    }
}
