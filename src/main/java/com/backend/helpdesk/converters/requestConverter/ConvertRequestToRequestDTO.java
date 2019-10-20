package com.backend.helpdesk.converters.requestConverter;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.converters.ProfileConverter.UserEntityToProfile;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.converters.requestTypeConverter.ConvertRequestTypeToRequestTypeDTO;
import com.backend.helpdesk.converters.statusConverter.ConvertStatusToStatusDTO;
import com.backend.helpdesk.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestToRequestDTO extends Converter<RequestEntity, RequestDTO> {

    @Autowired
    private UserEntityToProfile userEntityToProfile;

    @Autowired
    private ConvertRequestTypeToRequestTypeDTO convertRequestTypeToRequestTypeDTO;

    @Autowired
    private ConvertStatusToStatusDTO convertStatusToStatusDTO;

    @Override
    public RequestDTO convert(RequestEntity requestEntity){
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setId(requestEntity.getId());
        requestDTO.setUser(userEntityToProfile.convert(requestEntity.getUser()));
        requestDTO.setRequestType(convertRequestTypeToRequestTypeDTO.convert(requestEntity.getRequestType()));
        requestDTO.setStatus(convertStatusToStatusDTO.convert(requestEntity.getStatus()));
        requestDTO.setCreateAt(requestEntity.getCreateAt());
        requestDTO.setDayRequest(requestEntity.getDayRequest());
        requestDTO.setDescription(requestEntity.getDescription());

        return requestDTO;
    }
}
