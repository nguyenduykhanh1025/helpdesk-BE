package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Integer> {

    @Query(
            value = "SELECT * from categories as c where c.name like CONCAT('%', :keyword, '%') ORDER BY id",
            nativeQuery = true
    )
    List<CategoriesEntity> findAllByKeyword(@Param("keyword") String keyword);
}
