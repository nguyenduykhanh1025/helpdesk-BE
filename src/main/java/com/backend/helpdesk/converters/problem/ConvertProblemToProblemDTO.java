package com.backend.helpdesk.converters.problem;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.ProblemEntity;
import org.springframework.stereotype.Component;

@Component
public class ConvertProblemToProblemDTO extends Converter<ProblemEntity, ProblemDTO> {
    @Override
    public ProblemDTO convert(ProblemEntity problemEntity){
        ProblemDTO problemDTO = new ProblemDTO();

        problemDTO.setId(problemEntity.getId());
        problemDTO.setIdUser(problemEntity.getIdUser());
        problemDTO.setIdProblemType(problemEntity.getIdProblemType());
        problemDTO.setIdStatus(problemEntity.getIdStatus());
        problemDTO.setCreateAt(problemEntity.getCreateAt());
        problemDTO.setDayRequest(problemEntity.getDayRequest());
        problemDTO.setDescription(problemEntity.getDescription());

        return problemDTO;
    }
}
