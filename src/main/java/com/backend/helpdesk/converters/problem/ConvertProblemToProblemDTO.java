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
        problemDTO.setIdUser(problemEntity.getUser().getId());
        problemDTO.setIdProblemType(problemEntity.getProblemType().getId());
        problemDTO.setIdStatus(problemEntity.getStatus().getId());
        problemDTO.setCreateAt(problemEntity.getCreateAt());
        problemDTO.setDayRequest(problemEntity.getDayRequest());
        problemDTO.setDescription(problemEntity.getDescription());

        return problemDTO;
    }
}
