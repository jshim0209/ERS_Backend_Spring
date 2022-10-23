package com.revature.ers.controller;

import com.revature.ers.dto.AddReimbursementDto;
import com.revature.ers.dto.ReimbursementDto;
import com.revature.ers.model.Status;
import com.revature.ers.service.JwtService;
import com.revature.ers.service.ReimbursementService;
import com.revature.ers.service.UserService;
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
    private static UserService userService;

    @Autowired
    private static JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    ReimbursementDto rdto = new ReimbursementDto();

    @PostMapping("/users/{userId}/reimbursement")
    public ResponseEntity<ReimbursementDto> addReimbursement(@PathVariable ("userId") int userId, @RequestBody AddReimbursementDto ardto) {

        ReimbursementDto newReimbursement = reimbursementService.addReimbursement(userId, ardto);

        return ResponseEntity.ok(newReimbursement);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(reimbursementService.getAllStatuses());
    }

    @GetMapping("/reimbursements")
    public ResponseEntity<List<ReimbursementDto>> getAllReimbursements(@RequestParam Optional<Integer> status) {
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getAllReimbursements();
        if (status.isPresent()) {
            reimbursementDtos = reimbursementService.getReimbursementsByStatus(status);
        }
        return ResponseEntity.ok(reimbursementDtos);
    }

    @GetMapping("/users/{userId}/reimbursements")
    public ResponseEntity<List<ReimbursementDto>> getReimbursementsByUser(@PathVariable ("userId") Integer userId,
                                                                          @RequestParam Optional<Integer> status) {
        List<ReimbursementDto> reimbursementDtos = reimbursementService.getReimbursementsByUserId(userId);
        if (status.isPresent()) {
            reimbursementDtos = reimbursementService.getReimbursementsByUserIdAndStatus(userId, status);
        }
        return ResponseEntity.ok(reimbursementDtos);
    }

}
