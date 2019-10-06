package com.backend.helpdesk.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RequestDTO {

    private int id;

    @NotNull
    private int idProblemType;

    @NotNull
    private int idUser;

    private int idStatus;

    @NotNull
    private Date dayRequest;

    private Date createAt;

    @NotBlank
    private String description;
}
