package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("id_problem_type")
    private int idProblemType;

    @JsonAlias("id_user")
    private int idUser;

    private Date dayRequest;

    private Date createAt;

    private String description; //Tổ chức party thì phải thêm địa chỉ
}
