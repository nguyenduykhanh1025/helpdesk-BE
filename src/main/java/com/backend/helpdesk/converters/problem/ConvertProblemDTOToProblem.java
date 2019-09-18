package com.backend.helpdesk.converters.problem;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.ProblemEntity;
import org.springframework.stereotype.Component;

@Component
public class ConvertProblemDTOToProblem extends Converter<ProblemDTO, ProblemEntity> {
    @Override
    public ProblemEntity convert(ProblemDTO problemDTO){
        ProblemEntity problemEntity = new ProblemEntity();

        problemEntity.setId(problemDTO.getId());
        problemEntity.setIdUser(problemDTO.getIdUser());
        problemEntity.setIdProblemType(problemDTO.getIdProblemType());
        problemEntity.setIdStatus(problemDTO.getIdStatus());
        problemEntity.setCreateAt(problemDTO.getCreateAt());
        problemEntity.setDayRequest(problemDTO.getDayRequest());
        problemEntity.setDescription(problemDTO.getDescription());

        return problemEntity;
    }
}
