package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import com.backend.helpdesk.entity.SkillsEntity;
import com.backend.helpdesk.exception.CategoriesException.CategoriesNotFound;
import com.backend.helpdesk.exception.SkillsException.SkillsNotFound;
import com.backend.helpdesk.repository.CategoriesRepository;
import com.backend.helpdesk.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.backend.helpdesk.common.Constants.*;

@Component
public class SkillsService {

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    private Converter<SkillsEntity, Skills> skillsEntityToSkills;

    @Autowired
    private Converter<Skills, SkillsEntity> skillsToSkillsEntity;

    public Skills getSkillFollowId(int id) {
        if (skillsRepository.findById(id).isPresent()) {
            return skillsEntityToSkills.convert(skillsRepository.findById(id).get());
        }
        throw new SkillsNotFound();
    }

    public List<Skills> getAllSkills() {
        List<Skills> skillsList = new ArrayList<>();
        for (SkillsEntity skillsEntity : skillsRepository.findAll()) {
            skillsList.add(skillsEntityToSkills.convert(skillsEntity));
        }
        return skillsList;
    }

    public void addNewSkills(Skills skills) {

        SkillsEntity skillsEntity = skillsToSkillsEntity.convert(skills);

        if (isCategoriesExist(skillsEntity.getCategoriesEntity())) {
            skillsRepository.save(skillsEntity);
        } else {
            throw new CategoriesNotFound();
        }
    }

    public void editSkills(Skills skills) {

        if (skillsRepository.findById(skills.getId()).isPresent()) {

            SkillsEntity skillsEntity = skillsToSkillsEntity.convert(skills);

            if (isCategoriesExist(skillsEntity.getCategoriesEntity())) {
                skillsRepository.save(skillsEntity);
            } else {
                throw new CategoriesNotFound();
            }

        } else {
            throw new SkillsNotFound();
        }
    }

    public boolean isCategoriesExist(CategoriesEntity categoriesEntity) {
        if (categoriesRepository.findById(categoriesEntity.getId()).isPresent()) {
            // check name categories is exactly
            if (categoriesRepository.findById(categoriesEntity.getId()).get().getName().equals(categoriesEntity.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<Skills> getListItem(int sizeList, int indexPage, String valueSearch, int keySort) {
        List<Skills> skillsList = new ArrayList<>();

        Pageable sortedPage = PageRequest.of(indexPage, sizeList, Sort.by("id"));

        if (keySort == SORT_BY_NAME) {
            sortedPage = PageRequest.of(indexPage, sizeList, Sort.by("name"));
        } else if (keySort == SORT_BY_NAME_DESC) {
            sortedPage = PageRequest.of(indexPage, sizeList, Sort.by("name").descending());
        }

        for (SkillsEntity skillsEntity : skillsRepository.getSkillFollowValueSearch(valueSearch, sortedPage)) {
            skillsList.add(skillsEntityToSkills.convert(skillsEntity));
        }
        return skillsList;
    }

    public List<Skills> getSkillFollowIdCategories(int idCategories) {

        Optional<CategoriesEntity> categoriesEntityOpt = categoriesRepository.findById(idCategories);
        if (categoriesEntityOpt.isPresent()) {
            List<Skills> skillsList = new ArrayList<>();
            for (SkillsEntity skillsEntity : categoriesEntityOpt.get().getSkillsEntities()) {
                skillsList.add(skillsEntityToSkills.convert(skillsEntity));
            }
            return skillsList;
        }

        throw new CategoriesNotFound();
    }
}
