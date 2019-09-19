package com.backend.helpdesk.entityDTO;

import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class DayOffDTO {
    private int id;

    @NotBlank
    private Date dayStartOff;

    @NotBlank
    private float numberOfDayOff;

    @NotBlank
    private Date createAt;

    @NotBlank
    private String description;

    @NotBlank
    int dayOffType;

    @NotBlank
    int userEntity;

    @NotBlank
    int status;
}
