package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff,Integer> {

    List<DayOff> findByStatus(Status status);

    List<DayOff> findByUserEntity(UserEntity userEntity);
}
