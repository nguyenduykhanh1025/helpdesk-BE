package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.converters.problem.ConvertProblemDTOToProblem;
import com.backend.helpdesk.converters.problem.ConvertProblemToProblemDTO;
import com.backend.helpdesk.entity.ProblemEntity;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.repository.ProblemRepository;
import com.backend.helpdesk.repository.ProblemTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @Autowired
    private ConvertProblemDTOToProblem convertProblemDTOToProblem;

    @Autowired
    private ConvertProblemToProblemDTO convertProblemToProblemDTO;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProblemEntity> getAllProblem(){
        return problemRepository.findAll();
    }

    public ProblemDTO postProlem(ProblemDTO problemDTO){
        problemDTO.setId(0);
        problemDTO.setIdStatus(statusRepository.findByName("STATUS_WAITING").getId());
        return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));
    }

    public ProblemDTO putProblem(ProblemDTO problemDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isAdmin = false;

        for(RoleEntity role: userRepository.findByEmail(username).getRoleEntities()){
            if(role.getName()=="ROLE_ADMIN") isAdmin=true;
        }

        if (isAdmin)
        return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));
        else {
            problemDTO.setIdStatus(statusRepository.findByName("STATUS_WAITING").getId());
            return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));

        }
    }

    public void delProblem(@RequestParam int id){
        problemRepository.deleteById(id);
    }
}
