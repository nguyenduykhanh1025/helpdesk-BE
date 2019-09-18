package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.entity.ProblemEntity;
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
    public List<ProblemEntity> getAllProblem(){
        return problemService.getAllProblem();
    }

    @PostMapping
    public ProblemDTO postProlem(@RequestBody @Valid ProblemDTO problemDTO){
        return problemService.postProlem(problemDTO);
    }

    @PutMapping
    public ProblemDTO putProblem(@RequestBody @Valid ProblemDTO problemDTO){
        return problemService.putProblem(problemDTO);
    }

    @DeleteMapping
    public void delProblem(@RequestParam int id){
        problemService.delProblem(id);
    }
}
