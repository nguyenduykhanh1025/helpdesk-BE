package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {
    List <ProblemEntity> findByProblemTypeName(String problemType);
    List <ProblemEntity> findByUserEmail(String email);
    List <ProblemEntity> findByStatusName(String statusName);
}
