package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/day_offs")
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @GetMapping
    public List<DayOff> getAllDayOffs() {
        return dayOffService.getAllDayOff();
    }

    @GetMapping("status")
    public List<DayOff> getDayOffByEnable(@RequestParam(value = "status", required = false) String enable) {
        return dayOffService.getDayOffsByStatus(enable);
    }

    @GetMapping("/user/{id}")
    public List<DayOff> getDayOffByUser(@PathVariable("id") int id) {
        return dayOffService.getDayOffByUser(id);
    }

    @GetMapping("/the_number_of_day_off_by_user/{id}")
    public int getNumberOfDayOffByUser(@PathVariable("id") int id) {
        return dayOffService.getNumberOfDayOffByUser(id);
    }

    @GetMapping("/the_rest_day_off_by_user/{id}")
    public float getNumberTheRestDayOffByUser(@PathVariable("id") int id) {
        return dayOffService.getNumberTheRestDayOffByUser(id);
    }
}
