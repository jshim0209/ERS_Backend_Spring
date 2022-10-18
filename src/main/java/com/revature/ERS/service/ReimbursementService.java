package com.revature.ERS.service;

import com.revature.ERS.dto.AddReimbursementDto;
import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.model.Reimbursement;
import com.revature.ERS.model.Status;
import com.revature.ERS.model.User;
import com.revature.ERS.repository.ReimbursementRepository;
import com.revature.ERS.repository.ReimbursementStatusRepository;
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
    ReimbursementStatusRepository statusRepo;

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
        List<Status> statuses = statusRepo.findAll();

        return statuses;
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
