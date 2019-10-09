package com.backend.helpdesk.converters.request;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.RequestEntity;
import com.backend.helpdesk.repository.RequestTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestDTOToRequest extends Converter<RequestDTO, RequestEntity> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public RequestEntity convert(RequestDTO requestDTO){
        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setId(requestDTO.getId());
        requestEntity.setUser(userRepository.findById(requestDTO.getIdUser()).get());
        requestEntity.setRequestType(requestTypeRepository.findById(requestDTO.getIdRequestType()).get());
        requestEntity.setStatus(statusRepository.findById(requestDTO.getIdStatus()).get());
        requestEntity.setDayRequest(requestDTO.getDayRequest());
        requestEntity.setDescription(requestDTO.getDescription());

        return requestEntity;
    }
}
