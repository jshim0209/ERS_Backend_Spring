package com.revature.ERS.service;

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

    public UserDto getUserById(int id) {
        Optional<User> optional = userRepo.findById(id);

        if(optional.isPresent()) {
            return modelMapper.map(optional.get(), UserDto.class);
        }
        return null;
    }

    public UserDto createUser(User user) throws UserExistsException {

        User existingUser = userRepo.findByUsernameAndEmail(user.getUsername(), user.getEmail());

        if (existingUser != null) {

            throw new UserExistsException("An account exists with username of " + user.getUsername() + " and/or emaild address of " + user.getEmail());

        } else {
            return modelMapper.map(userRepo.save(user), UserDto.class);
        }
    }
}
