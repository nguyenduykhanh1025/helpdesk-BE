package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.RequestTypeDTO;
import com.backend.helpdesk.converters.requestTypeConverter.ConvertRequestTypeDTOToRequestType;
import com.backend.helpdesk.converters.requestTypeConverter.ConvertRequestTypeToRequestTypeDTO;
import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.repository.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestTypeService {
    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private ConvertRequestTypeDTOToRequestType convertRequestTypeDTOToRequestType;

    @Autowired
    private ConvertRequestTypeToRequestTypeDTO convertRequestTypeToRequestTypeDTO;

    public RequestTypeDTO getRequestTypeById(int id){
        return convertRequestTypeToRequestTypeDTO.convert(requestTypeRepository.findById(id).get());
    }

    public List<RequestTypeDTO> getAllRequestType(){

        List<RequestTypeDTO> requestTypeDTOList = new ArrayList<>();

        List<RequestType> requestTypeList = requestTypeRepository.findByOrderByNameAsc();

        for(RequestType requestType : requestTypeList){
            requestTypeDTOList.add(convertRequestTypeToRequestTypeDTO.convert(requestType));
        }

        return requestTypeDTOList;
    }

    public RequestTypeDTO addRequestType(String nameRequestType){
        if(!requestTypeRepository.findByName(nameRequestType).isPresent()){
            return convertRequestTypeToRequestTypeDTO.convert(requestTypeRepository.save(new RequestType(nameRequestType)));
        }
        return null;
    }

    public List<RequestTypeDTO> updateRequestType(RequestTypeDTO requestType){
        requestTypeRepository.save(convertRequestTypeDTOToRequestType.convert(requestType));
        return getAllRequestType();
    }

    public void deleteRequestType(int id) {
        requestTypeRepository.deleteById(id);
    }
}
