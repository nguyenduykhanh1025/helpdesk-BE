package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/{id}")
    public Categories getFollowId(@PathVariable("id") int id) {
        return categoriesService.getFollowId(id);
    }

    @GetMapping
    public List<Categories> getAll() {
        return categoriesService.getAll();
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public void addNewItem(@RequestBody @Validated Categories categories) {
        categoriesService.addNewItem(categories);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteItemFollowId(@PathVariable("id") int id) {
        categoriesService.deleteItemFollowId(id);
    }

    @GetMapping("/search")
    public List<Categories> searchCategories(@RequestParam(name = "valueSearch") String valueSearch) {
        return categoriesService.searchCategories(valueSearch);
    }
}