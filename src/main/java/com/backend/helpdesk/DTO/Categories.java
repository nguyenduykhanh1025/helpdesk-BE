package com.backend.helpdesk.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Categories {

    int id;

    @NotEmpty
    @NotBlank
    String name;

    public Categories() {
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
