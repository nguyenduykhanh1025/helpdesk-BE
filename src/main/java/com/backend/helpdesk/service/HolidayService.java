package com.backend.helpdesk.service;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.entity.Holiday;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private CommonMethods commonMethods;

    public List<Date> getHolidayThisYear(int year){
        List <Holiday> holidays=holidayRepository.findHolidayByYear(year);
        List<Date> result=new ArrayList<>();
        for(Holiday holiday:holidays){
            result.addAll(commonMethods.getListDateBetweenTwoDate(holiday.getDayStartOff(),holiday.getDayEndOff()));
        }
        return result;
    }

    public Holiday postHoliday(Holiday holiday){
        return holidayRepository.save(holiday);
    }

    public void deleteHoliday(int id){
        Holiday holiday=holidayRepository.findById(id);
        if(holiday==null){
            throw new NotFoundException("Holiday not found!");
        }
        holidayRepository.delete(holiday);
    }
}
