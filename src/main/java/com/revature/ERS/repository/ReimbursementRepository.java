package com.revature.ERS.repository;

import com.revature.ERS.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

//    @Query("select r from Reimbursement r where r.author.id=?1")
    List<Reimbursement> findByAuthorId (int authorId);

//    @Query("select r from Reimbursement r where r.status.id=?1")
    List<Reimbursement> findByStatusId (Optional<Integer> statusId);

//    @Query("select r from Reimbursement r where r.author.id=? and r.status.id=?")
    List<Reimbursement> findByAuthorIdAndStatusId (int authorId, Optional<Integer> statusId);
}
