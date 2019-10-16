package com.backend.helpdesk.controller;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entity.Holiday;
import com.backend.helpdesk.repository.DayOffRepository;
import com.backend.helpdesk.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public List<Date> getAllHoliday(@RequestParam(value = "year", required = false) int year){
        return holidayService.getHolidayThisYear(year);
    }

    @PostMapping
    public Holiday postHoliday(@Valid @RequestBody Holiday holiday){
        return holidayService.postHoliday(holiday);
    }

    @DeleteMapping("/{id}")
    public void deleteHoliday(@PathVariable("id") int id){
        holidayService.deleteHoliday(id);
    }
}
