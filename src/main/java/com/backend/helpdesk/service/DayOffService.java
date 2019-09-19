package com.backend.helpdesk.service;

import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.entityDTO.DayOffDTO;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.DayOffRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DayOffService {
    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DayOff> getAllDayOff() {
        return dayOffRepository.findAll();
    }

    public List<DayOff> getDayOffsByStatus(String enable) {
        Status status = statusRepository.findByName(enable);
        if (status == null) {
            throw new NotFoundException("Day off not found!");
        }
        return dayOffRepository.findByStatus(status);
    }

    public List<DayOff> getDayOffByUser(int id) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("Day off not found!");
        }
        return dayOffRepository.findByUserEntity(userEntity);
    }
}
