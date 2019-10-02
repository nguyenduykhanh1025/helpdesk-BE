package com.backend.helpdesk.DTO;

import lombok.Data;

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
