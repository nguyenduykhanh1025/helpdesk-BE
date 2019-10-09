package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "requests")
@NoArgsConstructor
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonAlias("id")
    private int id;

    @JsonAlias("id_request_type")
    @ManyToOne(cascade = CascadeType.ALL)
    private RequestType requestType;

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
