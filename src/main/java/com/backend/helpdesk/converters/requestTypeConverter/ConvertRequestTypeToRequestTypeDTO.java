package com.backend.helpdesk.converters.requestTypeConverter;

import com.backend.helpdesk.DTO.RequestTypeDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.RequestType;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestTypeToRequestTypeDTO extends Converter<RequestType, RequestTypeDTO> {
    @Override
    public RequestTypeDTO convert(RequestType requestType){
        RequestTypeDTO requestTypeDTO = new RequestTypeDTO();

        requestTypeDTO.setId(requestType.getId());
        requestTypeDTO.setName(requestType.getName());

        return requestTypeDTO;
    }
}
