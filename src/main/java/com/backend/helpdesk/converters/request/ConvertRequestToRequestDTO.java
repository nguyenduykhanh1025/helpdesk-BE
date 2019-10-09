package com.backend.helpdesk.converters.request;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.RequestEntity;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestToRequestDTO extends Converter<RequestEntity, RequestDTO> {
    @Override
    public RequestDTO convert(RequestEntity requestEntity){
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setId(requestEntity.getId());
        requestDTO.setIdUser(requestEntity.getUser().getId());
        requestDTO.setIdRequestType(requestEntity.getRequestType().getId());
        requestDTO.setIdStatus(requestEntity.getStatus().getId());
        requestDTO.setCreateAt(requestEntity.getCreateAt());
        requestDTO.setDayRequest(requestEntity.getDayRequest());
        requestDTO.setDescription(requestEntity.getDescription());

        return requestDTO;
    }
}
