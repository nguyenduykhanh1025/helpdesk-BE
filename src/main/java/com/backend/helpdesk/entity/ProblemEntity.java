package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "problems")
@NoArgsConstructor
public class ProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonAlias("id")
    private int id;

    @JsonAlias("id_problem_type")
    @ManyToOne(cascade = CascadeType.ALL)
    private ProblemType problemType;

    @JsonAlias("id_status")
    @ManyToOne(cascade = CascadeType.ALL)
    private Status status;

    @JsonAlias("id_user")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity user;

    private Date dayRequest;

    private Date createAt;

    private String description; //Tổ chức party thì phải thêm địa chỉ
}
