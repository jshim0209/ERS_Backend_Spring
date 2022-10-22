package com.revature.ers.dao;

import com.revature.ers.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    List<Reimbursement> findByAuthorId (int authorId);

    List<Reimbursement> findByStatusId (Optional<Integer> statusId);

    List<Reimbursement> findByAuthorIdAndStatusId (int authorId, Optional<Integer> statusId);
}
