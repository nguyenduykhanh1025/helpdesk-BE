package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.DayOffType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DayOffTypeRepository extends JpaRepository<DayOffType,Integer> {
    Optional<DayOffType> findByName(String name);
}
