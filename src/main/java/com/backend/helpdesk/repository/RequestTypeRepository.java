package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
    List<RequestType> findByName(String name);
}
