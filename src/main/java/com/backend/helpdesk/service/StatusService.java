package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converters.statusConverter.ConvertStatusToStatusDTO;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ConvertStatusToStatusDTO convertStatusToStatusDTO;

    public List<StatusDTO> getListStatus(){
        List<Status> statusList = statusRepository.findAll();
        List<StatusDTO> statusDTOList = new ArrayList<>();

        for (Status status: statusList){
            statusDTOList.add(convertStatusToStatusDTO.convert(status));
        }

        return statusDTOList;
    }
}
