package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {

    @Autowired
    SkillsService skillsService;

    @GetMapping("/{id}")
    public Skills getSkillFollowId(@PathVariable("id") int id) {
        return skillsService.getSkillFollowId(id);
    }

    @GetMapping
    public List<Skills> getAllSkills() {
        return skillsService.getAllSkills();
    }

    @Secured("ROLE_EMPLOYEES")
    @PostMapping
    public void addNewSkills(@RequestBody @Validated Skills skills) {
        skillsService.addNewSkills(skills);
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("/{id}")
    public void editSkills(@RequestBody @Validated Skills skills,
                           @PathVariable("id") int id) {
        skills.setId(id);
        skillsService.editSkills(skills);
    }

    @GetMapping("/pagination")
    public List<Skills> getListItem(@RequestParam(name = "sizeList") int sizeList,
                                    @RequestParam(name = "indexPage") int indexPage,
                                    @RequestParam(name = "valueSearch") String valueSearch,
                                    @RequestParam(name = "keySort") int keySort) {
        return skillsService.getListItem(sizeList, indexPage, valueSearch, keySort);
    }

    @GetMapping("/categories")
    public List<Skills> getSkillFollowIdCategories(@RequestParam int idCategories) {
        return skillsService.getSkillFollowIdCategories(idCategories);
    }
}
