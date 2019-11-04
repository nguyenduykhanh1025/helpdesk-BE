package com.backend.helpdesk.converters.statusConverter;

import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class ConvertStatusToStatusDTO extends Converter<Status, StatusDTO> {
    @Override
    public StatusDTO convert(Status status){
        StatusDTO statusDTO = new StatusDTO();

        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());

        return statusDTO;
    }
}
