package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.repository.DayOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DayOffService {
    @Autowired
    private DayOffRepository dayOffRepository;

    public List<DayOff> getAllDayOff(){
        return dayOffRepository.findAll();
    }



}
