package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff,Integer> {
    @Override
    List<DayOff> findAll();
}
