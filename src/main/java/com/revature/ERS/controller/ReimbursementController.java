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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "${ui.url}", allowCredentials = "true")
public class ReimbursementController {

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
    public ResponseEntity<ReimbursementDto> addReimbursement(@PathVariable ("userId") String userId, @RequestBody AddReimbursementDto ardto) {

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
    public ResponseEntity<List<ReimbursementDto>> getAllReimbursements(@RequestParam Optional<String> status) {
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getAllReimbursements();
        if (status.isPresent()) {
            reimbursementDtos = reimbursementService.getReimbursementsByStatus(status);
        }
        return ResponseEntity.ok(reimbursementDtos);
    }

    @GetMapping("/users/{userId}/reimbursements")
    public ResponseEntity<List<ReimbursementDto>> getReimbursementsByUser(@PathVariable ("userId") String userId
    ) {
        UserDto user = userService.getUserById(Integer.parseInt(userId));
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getReimbursementsByUser(modelMapper.map(user, User.class));

        return ResponseEntity.ok(reimbursementDtos);
    }
}
