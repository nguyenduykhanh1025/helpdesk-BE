package com.backend.helpdesk.converters.statusConverter;

import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusToStatusDTOConvert extends Converter<Status, StatusDTO> {

    @Override
    public StatusDTO convert(Status source) {
        StatusDTO statusDTO=new StatusDTO();
        statusDTO.setId(source.getId());
        statusDTO.setName(source.getName());
        return statusDTO;
    }
}
