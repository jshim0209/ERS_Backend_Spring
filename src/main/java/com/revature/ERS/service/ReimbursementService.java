package com.revature.ERS.service;

import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.model.Reimbursement;
import com.revature.ERS.model.User;
import com.revature.ERS.repository.ReimbursementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    @Autowired
    ReimbursementRepository reimbRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ReimbursementDto addReimbursement(Reimbursement reimbursement) {
        ReimbursementDto addedReimbursement = modelMapper.map(reimbRepo.save(reimbursement), ReimbursementDto.class);
        return addedReimbursement;
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

    public List<ReimbursementDto> getReimbursementsByUser(User user) {
        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findByUser(user);
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }

    public List<ReimbursementDto> getReimbursementsByStatus(Optional<String> status) {
        List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

        List<Reimbursement> reimbursements = reimbRepo.findByStatus(status);
        for (Reimbursement r : reimbursements) {
            reimbursementDtos.add(modelMapper.map(r, ReimbursementDto.class));
        }
        return reimbursementDtos;
    }


}
