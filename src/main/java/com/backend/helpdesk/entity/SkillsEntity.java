package com.backend.helpdesk.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "skills")
public class SkillsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    @ManyToOne
    @JoinColumn
    private CategoriesEntity categoriesEntity;

    @ManyToMany(mappedBy = "skillsEntities")
    private Set<UserEntity> userEntities;

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

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
