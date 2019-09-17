package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{status}")
    public List<DayOff> getDayOffByEnable(@PathVariable("status") String enable){
        return dayOffService.getDayOffsByStatus(enable);
    }
}
