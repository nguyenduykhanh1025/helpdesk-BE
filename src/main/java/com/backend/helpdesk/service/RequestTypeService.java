package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.repository.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTypeService {
    @Autowired
    private RequestTypeRepository requestTypeRepository;

    public RequestType getRequestTypeById(int id){
        return requestTypeRepository.findById(id).get();
    }

    public List<RequestType> getAllRequestType(){
        return requestTypeRepository.findAll();
    }

    public void addRequestType(String nameRequestType){
        if(!requestTypeRepository.findByName(nameRequestType).isPresent()){
            requestTypeRepository.save(new RequestType(nameRequestType));
        }
    }

    public void updateRequestType(RequestType requestType){
        requestTypeRepository.save(requestType);
    }

    public void deleteRequestType(int id) { requestTypeRepository.deleteById(id);}
}
