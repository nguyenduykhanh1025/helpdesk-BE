package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserEntity, Integer> {
}
