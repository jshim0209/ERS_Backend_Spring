package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.ERS.dto.AddReimbursementDto;
import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.model.Reimbursement;
import com.revature.ERS.model.Status;
import com.revature.ERS.model.User;
import com.revature.ERS.service.JwtService;
import com.revature.ERS.service.ReimbursementService;
import com.revature.ERS.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "*", allowedHeaders = "*")
public class ReimbursementController {

    Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    @Autowired
    private ReimbursementService reimbursementService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    ReimbursementDto rdto = new ReimbursementDto();

    @PostMapping("/users/{userId}/reimbursement")
    public ResponseEntity<?> addReimbursement(@RequestHeader ("Authorization") String headerValue,
                                              @PathVariable ("userId") String userId, @RequestBody AddReimbursementDto ardto) {

        String jwt = headerValue.split(" ")[1];

        Reimbursement addedReimbursement = new Reimbursement();

        UserDto user = userService.getUserById(Integer.parseInt(userId));
        Status pending = new Status(1, "pending");

        addedReimbursement.setAmount(ardto.getAmount());
        addedReimbursement.setDescription(ardto.getDescription());
        addedReimbursement.setReceipt(ardto.getReceipt());
        addedReimbursement.setTimeSubmitted(ardto.getTimeSubmitted());
        addedReimbursement.setType(ardto.getType());
        addedReimbursement.setAuthor(modelMapper.map(user, User.class));
        addedReimbursement.setStatus(pending);

        ReimbursementDto newReimb = reimbursementService.addReimbursement(addedReimbursement);

        return ResponseEntity.ok(newReimb);

    }

    @GetMapping("/reimbursements")
    public ResponseEntity<?> getAllReimbursements(@RequestHeader ("Authorization") String headerValue) {
            try {
                String jwt = headerValue.split(" ")[1];
                UserDto userDto = jwtService.parseJwt(jwt);

                System.out.println(userDto);

                if (Objects.equals(userDto.getUserRole(), "finance_manager")){
                    List<ReimbursementDto> reimbDtos = reimbursementService.getAllReimbursements();
                    return ResponseEntity.ok(reimbDtos);
                } else {
                    return ResponseEntity.status(401).body("You are not allowed to access this page");
                }
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(401).body(e.getMessage());
            }
    }

    @GetMapping("/users/{userId}/reimbursements")
    public ResponseEntity<?> getReimbursementsByUser(@RequestHeader ("Authorization") String headerValue,
                                                     @PathVariable ("userId") String userId) {
        try {
            String jwt = headerValue.split(" ")[1];

            UserDto userDto = jwtService.parseJwt(jwt);

            UserDto user = userService.getUserById(Integer.parseInt(userId));

            if (userDto.getId() == user.getId()) {

                List<ReimbursementDto> reimbDtos = reimbursementService.getReimbursementsByUser(modelMapper.map(user, User.class));

                return ResponseEntity.ok(reimbDtos);

            } else {
                return ResponseEntity.status(401).body("You are not allowed to acces this page");
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/reimbursements/filterBy")
    public ResponseEntity<?> getReimbursementsByStatus(@RequestHeader ("Authorization") String headerValue,
                                                       @RequestParam("status") Status status) {
        try {
            String jwt = headerValue.split(" ")[1];
            UserDto userDto = jwtService.parseJwt(jwt);

            System.out.println(userDto);

            if (Objects.equals(userDto.getUserRole(), "finance_manager")){
                List<ReimbursementDto> reimbDtos = new ArrayList<>();
                if (Objects.equals(status.getStatus(), "pending") ||
                        Objects.equals(status.getStatus(), "approved") ||
                        Objects.equals(status.getStatus(), "rejected")) {
                    reimbDtos = reimbursementService.getReimbursementsByStatus(status);
                }
                return ResponseEntity.ok(reimbDtos);
            } else {
                return ResponseEntity.status(401).body("You are not allowed to access this page");
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }
}
