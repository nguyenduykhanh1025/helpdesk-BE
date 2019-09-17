package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFollowId(@PathVariable("id") int id) {
        return categoriesService.getFollowId(id);
    }

    @GetMapping
    public List<Categories> getAll() {
        return categoriesService.getAll();
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public void addNewItem(@RequestHeader("Authorization") String token,
                           @RequestBody @Validated Categories categories) {
        categoriesService.addNewItem(categories);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemFollowId(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") int id) {
        return categoriesService.deleteItemFollowId(id);
    }

    @GetMapping("/search")
    public List<Categories> searchCategories(@RequestParam(name = "valueSearch") String valueSearch) {
        return categoriesService.searchCategories(valueSearch);
    }
}
