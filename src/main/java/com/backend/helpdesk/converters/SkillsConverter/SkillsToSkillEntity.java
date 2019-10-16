package com.backend.helpdesk.converters.SkillsConverter;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import com.backend.helpdesk.entity.SkillsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillsToSkillEntity extends Converter<Skills, SkillsEntity> {

    @Autowired
    private Converter<Categories, CategoriesEntity> categoriesToCategoriesEntity;

    @Override
    public SkillsEntity convert(Skills source) {
        SkillsEntity skillsEntity = new SkillsEntity();

        skillsEntity.setId(source.getId());
        skillsEntity.setName(source.getName());
        skillsEntity.setCategoriesEntity(categoriesToCategoriesEntity.convert(source.getCategories()));
        return skillsEntity;
    }
}
