package com.revature.ERS.repository;

import com.revature.ERS.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    @Query("select r from Reimbursement r where r.author.id=?1")
    List<Reimbursement> findByUser (int userId);

    @Query("select r from Reimbursement r where r.status.status=?1")
    List<Reimbursement> findByStatus (Optional<String> status);
}
