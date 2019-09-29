package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public List<ProblemDTO> getAllProblem(){
        return problemService.getAllProblem();
    }

    @GetMapping("/pagination-and-search")
    public List<ProblemDTO> searchProblemAndPagination(@RequestParam int page, @RequestParam int items, @RequestParam String sortBy, @RequestParam String search){
        return problemService.searchProblemAndPagination(page,items,sortBy,search);
    }

    @PostMapping
    public ProblemDTO addProblem(@RequestBody @Valid ProblemDTO problemDTO){
        return problemService.addProblem(problemDTO);
    }

    @PutMapping
    public ProblemDTO putProblem(@RequestBody @Valid ProblemDTO problemDTO){
        return problemService.putProblem(problemDTO);
    }

    @DeleteMapping
    public void removeProblem(@RequestParam int id){
        problemService.removeProblem(id);
    }
}
