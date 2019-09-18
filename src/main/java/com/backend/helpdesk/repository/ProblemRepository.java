package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}
