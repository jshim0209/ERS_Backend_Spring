package com.revature.ERS.repository;

import com.revature.ERS.model.Reimbursement;
import com.revature.ERS.model.Status;
import com.revature.ERS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    @Query("select r from Reimbursement r where r.author=?1")
    List<Reimbursement> findByUser (User user);

    List<Reimbursement> findByStatus (Status status);
}
