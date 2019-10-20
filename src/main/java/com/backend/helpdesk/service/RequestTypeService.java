package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.repository.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestTypeService {
    @Autowired
    private RequestTypeRepository requestTypeRepository;

    public RequestType getNameRequestTypeById(int id){
        return requestTypeRepository.findById(id).get();
    }

    public void addRequestType(String nameRequestType){
        if(!requestTypeRepository.findByName(nameRequestType).isPresent()){
            requestTypeRepository.save(new RequestType(nameRequestType));
        }
    }

    public void updateRequestType(RequestType requestType){
        requestTypeRepository.save(requestType);
    }
}
