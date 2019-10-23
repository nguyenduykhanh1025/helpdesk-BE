package com.backend.helpdesk.DTO;

import com.backend.helpdesk.entity.DayOffType;
import lombok.Data;

import java.util.Date;

@Data
public class DayOffDTO {
    private int id;

    private Date dayStartOff;

    private Date dayEndOff;

    private Date createAt;

    private String description;

    DayOffTypeDTO dayOffType;

    Profile userEntity;

    StatusDTO status;
}
