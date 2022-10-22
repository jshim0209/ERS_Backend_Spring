package com.revature.ERS.repository;

import com.revature.ERS.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementStatusRepository extends JpaRepository<Status, Integer> {
}
