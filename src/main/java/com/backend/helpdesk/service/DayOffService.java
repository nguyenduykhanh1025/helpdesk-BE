package com.backend.helpdesk.service;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DayOffService {
    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<DayOff, DayOffDTO> dayOffDayOffDTOConverter;

    @Autowired
    private Converter<DayOffDTO, DayOff> dayOffDTODayOffConverter;

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

    public long getNumberOfDayOffByUser(int id, int year) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        Date startingDay = userEntity.getStartingDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startingDay);
        int startingYear = calendar.get(Calendar.YEAR);
        if (startingYear > year) {
            throw new BadRequestException("Bad request!");
        }
        LocalDate startingLocalDate = startingDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.of(year, 12, 31);
        return ChronoUnit.YEARS.between(startingLocalDate, now) + Constants.DAYOFFBYRULE;
    }

    public float getNumberOfDayOffUsed(int id, int year) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        List<DayOff> dayOffs = dayOffRepository.getDayOffByYear(year, id);
        float result = 0;
        for (DayOff dayOff : dayOffs) {
            result += CommonMethods.calculateDaysBetweenTwoDate(dayOff.getDayStartOff(), dayOff.getDayEndOff());
        }
        int a = 0;
        return result;
    }

    public List<DayOffDTO> getListDayOffUsed(int id, int year) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        List<DayOff> dayOffs = dayOffRepository.getDayOffByYear(year, id);
        return dayOffDayOffDTOConverter.convert(dayOffs);
    }

    public float getNumberDayOffByUserRemaining(int id, int year) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        return getNumberOfDayOffByUser(id, year) - getNumberOfDayOffUsed(id, year);
    }

    public DayOff addDayOff(DayOffDTO dayOffDTO) {
        //number of day off register in request
        float numberOfDayOff = CommonMethods.calculateDaysBetweenTwoDate(dayOffDTO.getDayStartOff(), dayOffDTO.getDayEndOff());
        LocalDate localDateStart = dayOffDTO.getDayStartOff().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearStart = localDateStart.getYear();

        //number of day off remaining this year
        float numberOfDayOffRemainingThisYear = getNumberDayOffByUserRemaining(dayOffDTO.getUserEntity(), yearStart);
        if (numberOfDayOff > numberOfDayOffRemainingThisYear) {
            throw new BadRequestException("The number of days left is not enough!");
        }
        LocalDate localDateEnd = dayOffDTO.getDayStartOff().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearEnd = localDateStart.getYear();
        if (yearStart != yearEnd && yearEnd != Calendar.getInstance().get(Calendar.YEAR)) {
            throw new BadRequestException("Please register day off this year!");
        }
        Date date = new Date(System.currentTimeMillis());
        dayOffDTO.setCreateAt(date);
        dayOffDTO.setStatus(1);
        return dayOffRepository.save(dayOffDTODayOffConverter.convert(dayOffDTO));
    }

    public void deleteDayOff(int id) {
        DayOff dayOff = dayOffRepository.findById(id);
        if (dayOff == null) {
            throw new NotFoundException("Day off not found!");
        }
        dayOffRepository.delete(dayOff);
    }

    public List<DayOffDTO> pagination(int sizeList, int indexPage, String valueSearch) {
        if(indexPage<1){
            throw new BadRequestException("Page size must not be less than one");
        }
        List<DayOffDTO> dayOffDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(sizeList, indexPage);
        for (DayOff dayOff : dayOffRepository.searchDayOff(valueSearch, pageable)) {
            dayOffDTOS.add(dayOffDayOffDTOConverter.convert(dayOff));
        }
        return dayOffDTOS;
    }
}
