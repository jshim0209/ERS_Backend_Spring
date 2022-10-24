package com.revature.ers.service;

import com.revature.ers.dao.UserRepository;
import com.revature.ers.dto.AddReimbursementDto;
import com.revature.ers.dto.ReimbursementDto;
import com.revature.ers.dto.RestRequestUpdateStatusDto;
import com.revature.ers.dto.RestResponseUpdateStatusDto;
import com.revature.ers.exception.ResolvedStatusException;
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
    UserRepository userRepo;

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

    public Reimbursement getReimbursementById(int id) {

        return reimbRepo.getById(id);
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

    public RestResponseUpdateStatusDto resolveReimbursement(int id, RestRequestUpdateStatusDto dto) throws ResolvedStatusException {

        Reimbursement reimbursement = reimbRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reimbursement does not exist with id "
                + id));

        User resolver = userRepo.findById(dto.getResolverId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist with id "
                + dto.getResolverId()));

        reimbursement.setStatus(dto.getStatus());
        reimbursement.setResolver(resolver);
        reimbursement.setTimeResolved(dto.getTimeResolved());

        Reimbursement save = reimbRepo.save(reimbursement);

        return RestResponseUpdateStatusDto.builder()
                .id(save.getId())
                .udpatedStatus(save.getStatus())
                .updatedResolver(save.getResolver())
                .timeResolved(save.getTimeResolved())
                .build();
    }
}
