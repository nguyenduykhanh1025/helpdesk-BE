package com.backend.helpdesk.controller;
import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public List<RequestDTO> getAllRequest(){
        return requestService.getAllRequest();
    }

    @GetMapping("/pagination-and-search")
    public List<RequestDTO> searchRequestAndPagination(@RequestParam int page, @RequestParam int items, @RequestParam String sortBy, @RequestParam String search){
        return requestService.searchRequestAndPagination(page,items,sortBy,search);
    }

    @GetMapping("/get-size")
    public int getSize(@RequestParam String search){
        return requestService.getSize(search);
    }

    @PostMapping
    public RequestDTO addRequest(@RequestBody RequestDTO request){
        return requestService.addRequest(request);
    }

    public RequestDTO putRequest(@RequestBody @Valid RequestDTO requestDTO){
        return requestService.putRequest(requestDTO);
    }

    @DeleteMapping
    public void removeRequest(@RequestParam int id){
        requestService.removeRequest(id);
    }
}
