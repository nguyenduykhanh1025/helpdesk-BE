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
    public List<DayOff> getAllDayOffs(){
        return dayOffService.getAllDayOff();
    }

    @GetMapping("enable")
    public List<DayOff> getDayOffByEnable(@RequestParam(value = "enable",required = false) String enable){
        return dayOffService.getDayOffsByStatus(enable);
    }

    @GetMapping("/user/{id}")
    public List<DayOff> getDayOffByUser(@PathVariable("id") int id){
        return dayOffService.getDayOffByUser(id);
    }
}
