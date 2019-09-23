package com.backend.helpdesk.entityDTO;

import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class DayOffDTO {
    private int id;

    private Date dayStartOff;

    private Date dayEndOff;

    private Date createAt;

    private String description;

    int dayOffType;

    int userEntity;

    int status;
}
