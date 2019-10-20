package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RequestDTO {

    private int id;

    @NotNull
    private RequestTypeDTO requestType;

    @NotNull
    private Profile user;

    private StatusDTO status;

    @NotNull
    private Date dayRequest;

    private Date createAt;

    @NotBlank
    private String description;
}
