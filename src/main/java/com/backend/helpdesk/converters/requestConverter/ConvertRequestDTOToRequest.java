package com.backend.helpdesk.converters.requestConverter;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.converters.requestTypeConverter.ConvertRequestTypeDTOToRequestType;
import com.backend.helpdesk.converters.statusConverter.ConvertStatusDTOToStatus;
import com.backend.helpdesk.entity.RequestEntity;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestDTOToRequest extends Converter<RequestDTO, RequestEntity> {

    @Autowired
    private ConvertStatusDTOToStatus convertStatusDTOToStatus;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertRequestTypeDTOToRequestType convertRequestTypeDTOToRequestType;

    @Override
    public RequestEntity convert(RequestDTO requestDTO){
        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setId(requestDTO.getId());
        requestEntity.setUser(userRepository.findById(requestDTO.getUser().getId()).get());
        requestEntity.setRequestType(convertRequestTypeDTOToRequestType.convert(requestDTO.getRequestType()));
        requestEntity.setStatus(convertStatusDTOToStatus.convert(requestDTO.getStatus()));
        requestEntity.setCreateAt(requestDTO.getCreateAt());
        requestEntity.setDayRequest(requestDTO.getDayRequest());
        requestEntity.setDescription(requestDTO.getDescription());

        return requestEntity;
    }
}
