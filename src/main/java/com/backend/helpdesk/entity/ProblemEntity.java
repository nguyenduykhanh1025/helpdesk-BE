package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "problem")
public class ProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonAlias("id")
    private int id;

    @JsonAlias("id_problem_type")
    private ProblemType problemType;

    @JsonAlias("id_status")
    private Status status;

    @JsonAlias("id_user")
    private UserEntity user;

    private Date dayRequest;

    private Date createAt;

    private String description; //Tổ chức party thì phải thêm địa chỉ
}
