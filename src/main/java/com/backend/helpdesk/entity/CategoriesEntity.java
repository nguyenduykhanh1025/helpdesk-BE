package com.backend.helpdesk.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "categories")
public class CategoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    @OneToMany(mappedBy = "categoriesEntity", cascade = CascadeType.ALL)
    private List<SkillsEntity> skillsEntities;

    public CategoriesEntity() {
    }

    public CategoriesEntity(int id, String name) {
        this.id = id;
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

    public List<SkillsEntity> getSkillsEntities() {
        return skillsEntities;
    }

    public void setSkillsEntities(List<SkillsEntity> skillsEntities) {
        this.skillsEntities = skillsEntities;
    }
}
