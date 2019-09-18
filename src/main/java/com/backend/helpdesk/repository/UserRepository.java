package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    UserEntity findByEmail(String email);

    UserEntity findById(int id);
}
