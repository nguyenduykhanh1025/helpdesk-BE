package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.exception.FileException.FileNotFoundException;
import com.backend.helpdesk.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
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

    @Secured("ROLE_EMPLOYEES")
    @PutMapping("/avatars/{idUser}")
    public void editAvatar(@RequestParam MultipartFile avatar, @PathVariable("idUser") int idUser) throws IOException {
        if (avatar.isEmpty()) {
            throw new FileNotFoundException();
        }
        profileService.uploadAvatar(avatar.getBytes(), idUser);
    }

    @GetMapping("/user")
    public Profile getProfileFollowIdUser(@RequestParam int idUser) {
        return profileService.getProfileFollowIdUser(idUser);
    }

    @GetMapping("/search")
    public List<Profile> searchAllProfileByKeyword(String keyword) {
        return profileService.searchAllUserFollowKeyWord(keyword);
    }

    @GetMapping("/isAdmin")
    public boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.isAdmin(auth.getName());
    }
}
