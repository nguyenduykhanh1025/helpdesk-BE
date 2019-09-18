package com.backend.helpdesk.service;

import com.backend.helpdesk.entity.Problem;
import com.backend.helpdesk.repository.ProblemRepository;
import com.backend.helpdesk.repository.ProblemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    public List<Problem> getAllProblem(){
        return problemRepository.findAll();
    }

    public Problem postProlem(Problem problem){
        return problemRepository.save(problem);
    }
}
