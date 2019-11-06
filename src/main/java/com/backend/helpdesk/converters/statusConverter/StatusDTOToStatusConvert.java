//package com.backend.helpdesk.converters.statusConverter;
//
//import com.backend.helpdesk.DTO.StatusDTO;
//import com.backend.helpdesk.converters.bases.Converter;
//import com.backend.helpdesk.entity.Status;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StatusDTOToStatusConvert extends Converter<StatusDTO, Status> {
//    @Override
//    public Status convert(StatusDTO source) {
//        Status status=new Status();
//        status.setId(source.getId());
//        status.setName(source.getName());
//        return status;
//    }
//}
