package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday,Integer> {

    Holiday findById(int id);

    @Query(value = "SELECT * FROM holiday where day_start_off::::text like CONCAT('%', ?1, '%')",nativeQuery = true)
    List<Holiday> findHolidayByYear(int year);

}
