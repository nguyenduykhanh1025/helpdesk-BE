package com.backend.helpdesk.converters.statusConverter;

import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertStatusDTOToStatus extends Converter<StatusDTO, Status> {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status convert(StatusDTO statusDTO){
        return statusRepository.findById(statusDTO.getId()).get();
    }
}
