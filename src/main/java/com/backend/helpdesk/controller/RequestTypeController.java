package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.service.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request-types")
@CrossOrigin(origins = "http://localhost:4200")
public class RequestTypeController {
    @Autowired
    private RequestTypeService requestTypeService;

    @GetMapping("/{id}")
    public RequestType getRequestTypeById(@PathVariable int id){
        return requestTypeService.getRequestTypeById(id);
    }

    @GetMapping
    public List<RequestType> getAllRequestType(){
        return requestTypeService.getAllRequestType();
    }

    @PostMapping
    public void addRequestType(@RequestParam String nameRequestType){
        requestTypeService.addRequestType(nameRequestType);
    }

    @PutMapping
    public void updateRequestType(@RequestBody RequestType requestType){
        requestTypeService.updateRequestType(requestType);
    }

    @DeleteMapping("/{id}")
    public void deleteRequestType(@PathVariable int id){
        requestTypeService.deleteRequestType(id);
    }
}
