package com.backend.helpdesk.controller;
import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getRequestOfMe")
    public List<RequestDTO> getAllRequestOfUserLogin(){
        return this.requestService.getAllRequestOfUserLogin();
    }

    @PostMapping
    public RequestDTO addRequest(@RequestBody RequestDTO request){
        return requestService.addRequest(request);
    }

    @PutMapping
    public RequestDTO putRequest(@RequestBody @Valid RequestDTO requestDTO){
        return requestService.putRequest(requestDTO);
    }

    @PostMapping("/approveRequest/{id}/{keyAdmin}")
    public RequestDTO approvedRequest(@PathVariable int id, @PathVariable String keyAdmin){
        return requestService.approvedOrRejectRequest(id, keyAdmin, "APPROVED");
    }

    @PostMapping("rejectRequest/{id}/{keyAdmin}")
    public RequestDTO rejectRequest(@PathVariable int id, @PathVariable String keyAdmin){
        return requestService.approvedOrRejectRequest(id, keyAdmin, "REJECTED");
    }

    @DeleteMapping("/{id}")
    public void removeRequest(@PathVariable int id){
        requestService.removeRequest(id);
    }
}
