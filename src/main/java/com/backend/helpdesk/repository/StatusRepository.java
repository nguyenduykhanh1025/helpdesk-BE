package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {
    Status findByName(String name);
    Status findById(int id);
}
