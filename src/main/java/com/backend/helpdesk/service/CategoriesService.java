package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import com.backend.helpdesk.entity.SkillsEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.CategoriesException.CategoriesNotFound;
import com.backend.helpdesk.repository.CategoriesRepository;
import com.backend.helpdesk.repository.SkillsRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    private Converter<CategoriesEntity, Categories> categoriesEntityToCategories;

    @Autowired
    private Converter<Categories, CategoriesEntity> categoriesToCategoriesEntity;

    public Categories getFollowId(int id) {
        if (categoriesRepository.findById(id).isPresent()) {
            return categoriesEntityToCategories.convert(categoriesRepository.findById(id).get());
        }
        throw new CategoriesNotFound();
    }

    public List<Categories> getAll() {
        List<Categories> categoriesList = new ArrayList<>();

        for (CategoriesEntity categoriesEntity : categoriesRepository.findAll()) {
            categoriesList.add(categoriesEntityToCategories.convert(categoriesEntity));
        }
        return categoriesList;
    }

    public void addNewItem(Categories categories) {
        categoriesRepository.save(categoriesToCategoriesEntity.convert(categories));
    }

    public void deleteItemFollowId(int id) {
        Optional<CategoriesEntity> categoriesEntityOpt = categoriesRepository.findById(id);
        if (categoriesEntityOpt.isPresent()) {
            CategoriesEntity categoriesEntity = categoriesEntityOpt.get();
            for (SkillsEntity skillsEntity : categoriesEntity.getSkillsEntities()) {
                // delete skill in table user
                for (UserEntity user : skillsEntity.getUserEntities()) {
                    user.getSkillsEntities().remove(skillsEntity);
                    userRepository.save(user);
                }
                skillsRepository.delete(skillsEntity);
            }
            categoriesRepository.delete(categoriesEntity);
        } else {
            throw new CategoriesNotFound();
        }
    }

    public List<Categories> searchCategories(String valueSearch) {
        List<Categories> categoriesList = new ArrayList<>();
        for (CategoriesEntity categoriesEntity : categoriesRepository.findAllByKeyword(valueSearch)) {
            categoriesList.add(categoriesEntityToCategories.convert(categoriesEntity));
        }
        return categoriesList;
    }

    public void editCategories(Categories categories) {
        Optional<CategoriesEntity> categoriesEntityOpt = categoriesRepository.findById(categories.getId());
        if (categoriesEntityOpt.isPresent()) {
            CategoriesEntity categoriesEntity = categoriesEntityOpt.get();
            categoriesEntity.setName(categories.getName());
            categoriesRepository.save(categoriesEntity);
        } else {
            throw new CategoriesNotFound();
        }
    }

}
