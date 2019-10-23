package com.backend.helpdesk.converters.DayOffTypeConverter;

import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.DayOffType;
import org.springframework.stereotype.Component;

@Component
public class DayOffTypeDTOToDayOffTypeConvert extends Converter<DayOffTypeDTO, DayOffType> {

    @Override
    public DayOffType convert(DayOffTypeDTO source) {
        DayOffType dayOffType=new DayOffType();
        dayOffType.setId(source.getId());
        dayOffType.setName(source.getName());
        return dayOffType;
    }
}
