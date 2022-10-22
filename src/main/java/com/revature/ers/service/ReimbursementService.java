package com.revature.ers.service;

import com.revature.ers.dto.AddReimbursementDto;
import com.revature.ers.dto.ReimbursementDto;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.model.Status;
import com.revature.ers.model.User;
import com.revature.ers.dao.ReimbursementRepository;
import com.revature.ers.dao.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    @Autowired
    UserService userService;

    @Autowired
    ReimbursementRepository reimbRepo;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ReimbursementDto addReimbursement(int userId, AddReimbursementDto reimbDto) {
        User user = userService.getUserById(userId);
        Status pending = new Status(1, "Pending");

        Reimbursement reimbursement = new Reimbursement(
                0, reimbDto.getAmount(), reimbDto.getTimeSubmitted(), null, reimbDto.getDescription(),
                reimbDto.getReceipt(), user, null, pending, reimbDto.getType()
        );

        Reimbursement addedReimbursement = reimbRepo.save(reimbursement);

        return modelMapper.map(addedReimbursement, ReimbursementDto.class);
    }

    public List<Status> getAllStatuses() {
        return statusRepo.findAll();
    }

    public List<ReimbursementDto> getAllReimbursements() {

        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findAll();
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }

    public ReimbursementDto getReimbursementById(int id) {

        Optional<Reimbursement> optional = reimbRepo.findById(id);

        if(optional.isPresent()) {
            return modelMapper.map(optional.get(), ReimbursementDto.class);

        }
        return null;
    }

    public List<ReimbursementDto> getReimbursementsByUserId(int authorId) {
        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findByAuthorId(authorId);
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }

    public List<ReimbursementDto> getReimbursementsByStatus(Optional<Integer> statusId) {
        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findByStatusId(statusId);
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }

    public List<ReimbursementDto> getReimbursementsByUserIdAndStatus(int authorId, Optional<Integer> statusId) {
        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findByAuthorIdAndStatusId(authorId, statusId);
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }


}
