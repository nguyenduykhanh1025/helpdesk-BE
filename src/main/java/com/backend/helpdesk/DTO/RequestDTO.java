package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RequestDTO {

    private int id;

    @NotNull
    private int idRequestType;

    @NotNull
    private int idUser;

    private int idStatus;

    @NotNull
    private Date dayRequest;

    @NotBlank
    private String description;
}
