package com.backend.helpdesk.converters.SkillsConverter;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import com.backend.helpdesk.entity.SkillsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillsEntityToSkills extends Converter<SkillsEntity, Skills> {

    @Autowired
    private Converter<CategoriesEntity, Categories> categoriesEntityToCategories;

    @Override
    public Skills convert(SkillsEntity source) {

        Skills skill = new Skills();

        skill.setId(source.getId());
        skill.setName(source.getName());
        skill.setCategories(categoriesEntityToCategories.convert(source.getCategoriesEntity()));

        return skill;
    }
}
