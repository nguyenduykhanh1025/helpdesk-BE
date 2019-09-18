package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemTypeRepository extends JpaRepository<ProblemType, Integer> {
    public List<ProblemType> findByName(String name);
}
