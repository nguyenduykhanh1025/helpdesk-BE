package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.DayOffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayOffTypeService {

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    public List<DayOffType> getAllDayOffType() {
        return dayOffTypeRepository.findAll();
    }

    public DayOffType addDayOffType(DayOffType dayOffType) {
        if (dayOffTypeRepository.findByName(dayOffType.getName()) != null) {
            throw new BadRequestException("Day off type is existed");
        }
        return dayOffTypeRepository.save(dayOffType);
    }

    public void deleteDayOffType(int id) {
        if(dayOffTypeRepository.findById(id)==null){
            throw new NotFoundException("Day off type not found!");
        }
        dayOffTypeRepository.deleteById(id);
    }
}
