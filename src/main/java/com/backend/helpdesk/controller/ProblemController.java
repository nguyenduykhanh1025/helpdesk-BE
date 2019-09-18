package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.Problem;
import com.backend.helpdesk.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public List<Problem> getAllProblem(){
        return problemService.getAllProblem();
    }

    @PostMapping
    public Problem postProlem(@RequestBody Problem problem){
        return problemService.postProlem(problem);
    }
}
