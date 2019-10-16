package com.backend.helpdesk.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    @Column(nullable = false)
    private String name;

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
