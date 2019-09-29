package com.backend.helpdesk.converters.problem;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.ProblemEntity;
import com.backend.helpdesk.repository.ProblemTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertProblemDTOToProblem extends Converter<ProblemDTO, ProblemEntity> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public ProblemEntity convert(ProblemDTO problemDTO){
        ProblemEntity problemEntity = new ProblemEntity();

        problemEntity.setId(problemDTO.getId());
        problemEntity.setUser(userRepository.findById(problemDTO.getIdUser()).get());
        problemEntity.setProblemType(problemTypeRepository.findById(problemDTO.getIdProblemType()).get());
        problemEntity.setStatus(statusRepository.findById(problemDTO.getIdStatus()).get());
        problemEntity.setCreateAt(problemDTO.getCreateAt());
        problemEntity.setDayRequest(problemDTO.getDayRequest());
        problemEntity.setDescription(problemDTO.getDescription());

        return problemEntity;
    }
}
