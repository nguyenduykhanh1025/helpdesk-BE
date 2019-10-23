package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.DayOffTypeRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayOffTypeService {

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    @Autowired
    private Converter<DayOffType, DayOffTypeDTO> dayOffTypeDayOffTypeDTOConverter;

    @Autowired
    private Converter<DayOffTypeDTO, DayOffType> dayOffTypeDTODayOffTypeConverter;

    public List<DayOffTypeDTO> getAllDayOffType() {
        return dayOffTypeDayOffTypeDTOConverter.convert(dayOffTypeRepository.findAll());
    }

    public DayOffType addDayOffType(DayOffTypeDTO dayOffTypeDTO) {
        Optional<DayOffType> dayOffType=dayOffTypeRepository.findByName(dayOffTypeDTO.getName());
        if (dayOffType.isPresent()) {
            throw new BadRequestException("Day off type is existed");
        }
        return dayOffTypeRepository.save(dayOffTypeDTODayOffTypeConverter.convert(dayOffTypeDTO));
    }

    public void deleteDayOffType(int id) {
        Optional<DayOffType> dayOffType=dayOffTypeRepository.findById(id);
        if (!dayOffType.isPresent()) {
            throw new NotFoundException("Day off type not found!");
        }
        throw new BadRequestException("Bad request");
    }

    public DayOffType editDayOffType(int id, DayOffTypeDTO dayOffTypeDTO) {
        Optional<DayOffType> dayOffType = dayOffTypeRepository.findById(id);
        if (!dayOffType.isPresent()) {
            throw new NotFoundException("day off type not found");
        }
        Optional<DayOffType> dayOffType1=dayOffTypeRepository.findByName(dayOffTypeDTO.getName());
        if (dayOffType1.isPresent()) {
            throw new BadRequestException("Day off type is existed");
        }
        dayOffType.get().setName(dayOffTypeDTO.getName());
        return dayOffTypeRepository.save(dayOffType.get());
    }
}
