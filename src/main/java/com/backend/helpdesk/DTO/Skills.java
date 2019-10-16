package com.backend.helpdesk.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Skills {
    int id;

    @NotEmpty
    @NotBlank
    String name;

    Categories categories;

    public Skills() {
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

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
