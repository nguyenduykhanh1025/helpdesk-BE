package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.DayOffRepository;
import com.backend.helpdesk.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DayOffService {
    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<DayOff> getAllDayOff(){
        return dayOffRepository.findAll();
    }

    public List<DayOff> getDayOffsByStatus(String enable){
        Status status=statusRepository.findByName(enable);
        if(status==null){
            throw new NotFoundException("Enable isn't exist");
        }
        return dayOffRepository.findByStatus(status);
    }
}
