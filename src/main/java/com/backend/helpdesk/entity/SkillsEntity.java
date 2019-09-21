package com.backend.helpdesk.entity;

import javax.persistence.*;

@Entity(name = "skills")
public class SkillsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    @ManyToOne
    @JoinColumn
    private CategoriesEntity categoriesEntity;

    public SkillsEntity() {
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

    public CategoriesEntity getCategoriesEntity() {
        return categoriesEntity;
    }

    public void setCategoriesEntity(CategoriesEntity categoriesEntity) {
        this.categoriesEntity = categoriesEntity;
    }
}
