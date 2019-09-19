package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping
    public Profile getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.getProfile(auth.getName());
    }

    @Secured("ROLE_EMPLOYEES")
    @PutMapping
    public void editProfile(@RequestBody Profile profile) {
        profileService.editProfile(profile);
    }
}
