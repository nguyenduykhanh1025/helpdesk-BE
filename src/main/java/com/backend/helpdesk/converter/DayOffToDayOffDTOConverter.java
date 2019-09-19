package com.backend.helpdesk.converter;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entityDTO.DayOffDTO;
import org.springframework.stereotype.Component;

@Component
public class DayOffToDayOffDTOConverter extends Converter<DayOff, DayOffDTO> {
    @Override
    public DayOffDTO convert(DayOff source) {
        DayOffDTO dayOffDTO=new DayOffDTO();
        dayOffDTO.setId(source.getId());
        dayOffDTO.setCreateAt(source.getCreateAt());
        dayOffDTO.setDayStartOff(source.getDayStartOff());
        dayOffDTO.setNumberOfDayOff(source.getNumberOfDayOff());
        dayOffDTO.setDescription(source.getDescription());
        dayOffDTO.setDayOffType(source.getDayOffType().getId());
        dayOffDTO.setStatus(source.getStatus().getId());
        dayOffDTO.setUserEntity(source.getId());
        return dayOffDTO;
    }
}
