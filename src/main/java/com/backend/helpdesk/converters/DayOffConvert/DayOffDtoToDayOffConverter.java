package com.backend.helpdesk.converters.DayOffConvert;
import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.DTO.DayOffDTO;
import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayOffDtoToDayOffConverter extends Converter<DayOffDTO, DayOff> {

    @Autowired
    private Converter<Profile, UserEntity> profileUserEntityConverter;

    @Autowired
    private Converter<StatusDTO, Status> statusDTOStatusConverter;

    @Autowired
    private Converter<DayOffTypeDTO, DayOffType> dayOffTypeDTODayOffTypeConverter;

    @Override
    public DayOff convert(DayOffDTO source) {
        DayOff dayOff=new DayOff();
        dayOff.setId(source.getId());
        dayOff.setCreateAt(source.getCreateAt());
        dayOff.setDayStartOff(source.getDayStartOff());
        dayOff.setDayEndOff(source.getDayEndOff());
        dayOff.setDescription(source.getDescription());
        dayOff.setUserEntity(profileUserEntityConverter.convert(source.getUserEntity()));
        dayOff.setStatus(statusDTOStatusConverter.convert(source.getStatus()));
        dayOff.setDayOffType(dayOffTypeDTODayOffTypeConverter.convert(source.getDayOffType()));
        return dayOff;
    }
}
