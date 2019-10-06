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
    public List<RequestDTO> getAllProblem(){
        return requestService.getAllProblem();
    }

    @GetMapping("/pagination-and-search")
    public List<RequestDTO> searchProblemAndPagination(@RequestParam int page, @RequestParam int items, @RequestParam String sortBy, @RequestParam String search){
        return requestService.searchProblemAndPagination(page,items,sortBy,search);
    }

    @PostMapping
    public RequestDTO addProblem(@RequestBody @Valid RequestDTO requestDTO){
        return requestService.addProblem(requestDTO);
    }

    @PutMapping
    public RequestDTO putProblem(@RequestBody @Valid RequestDTO requestDTO){
        return requestService.putProblem(requestDTO);
    }

    @DeleteMapping
    public void removeProblem(@RequestParam int id){
        requestService.removeProblem(id);
    }
}
