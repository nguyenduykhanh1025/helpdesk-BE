package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.SkillsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillsRepository extends JpaRepository<SkillsEntity, Integer> {
    @Query(
            value = "SELECT * FROM skills\n" +
                    "where name like CONCAT('%', :valueSearch , '%')",
            nativeQuery = true
    )
    List<SkillsEntity> getSkillFollowValueSearch(@Param("valueSearch") String valueSearch, Pageable pageable);
}
