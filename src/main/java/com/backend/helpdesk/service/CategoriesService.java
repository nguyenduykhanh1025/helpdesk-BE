package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import com.backend.helpdesk.exception.CategoriesException.CategoriesNotFound;
import com.backend.helpdesk.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    AuthenticationService authenticationService;

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
        if (categoriesRepository.findById(id).isPresent()) {
            categoriesRepository.delete(categoriesRepository.findById(id).get());
        }else {
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
}
