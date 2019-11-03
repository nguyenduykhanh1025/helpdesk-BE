package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
    Optional<RequestType> findByName(String name);
    List<RequestType> findByOrderByNameAsc();
}
