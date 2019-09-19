package com.backend.helpdesk.converter;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entityDTO.DayOffDTO;
import com.backend.helpdesk.repository.DayOffTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayOffDtoToDayOffConverter extends Converter<DayOffDTO, DayOff> {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    @Override
    public DayOff convert(DayOffDTO source) {
        DayOff dayOff=new DayOff();
        dayOff.setId(source.getId());
        dayOff.setCreateAt(source.getCreateAt());
        dayOff.setDayStartOff(source.getDayStartOff());
        dayOff.setNumberOfDayOff(source.getNumberOfDayOff());
        dayOff.setDescription(source.getDescription());
        dayOff.setUserEntity(userRepository.findById(source.getUserEntity()));
        dayOff.setStatus(statusRepository.findById(source.getStatus()));
        dayOff.setDayOffType(dayOffTypeRepository.findById(source.getDayOffType()));
        return dayOff;
    }
}
