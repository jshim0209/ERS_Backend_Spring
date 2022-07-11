package com.revature.ERS.controller;

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
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
    public ResponseEntity<?> getAllReimbursements(@RequestParam Optional<String> status) {
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getAllReimbursements();
        if (status.isPresent()) {
            reimbursementDtos = reimbursementService.getReimbursementsByStatus(status);
        }
        return ResponseEntity.ok(reimbursementDtos);
    }

    @GetMapping("/users/{userId}/reimbursements")
    public ResponseEntity<?> getReimbursementsByUser(@PathVariable ("userId") String userId
//            , @RequestParam Optional<String> status
    ) {
        UserDto user = userService.getUserById(Integer.parseInt(userId));
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getReimbursementsByUser(modelMapper.map(user, User.class));
//        if (status.isPresent()) {
//            reimbursementDtos = reimbursementService.getReimbursementsByStatus(status);
//        }
        return ResponseEntity.ok(reimbursementDtos);
    }

}
