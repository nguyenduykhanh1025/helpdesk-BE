package com.backend.helpdesk.converters.requestTypeConverter;

import com.backend.helpdesk.DTO.RequestTypeDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.repository.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestTypeDTOToRequestType extends Converter<RequestTypeDTO, RequestType> {

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Override
    public RequestType convert(RequestTypeDTO requestTypeDTO){
        return requestTypeRepository.findById(requestTypeDTO.getId()).get();
    }
}
