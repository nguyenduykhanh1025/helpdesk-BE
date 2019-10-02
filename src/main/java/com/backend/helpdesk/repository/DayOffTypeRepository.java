package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.DayOffType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffTypeRepository extends JpaRepository<DayOffType,Integer> {
    DayOffType findById(int id);
    DayOffType findByName(String name);
}
