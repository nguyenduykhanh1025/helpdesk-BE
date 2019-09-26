package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<Profile> getAllUser() {
        return userService.getAllUser();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{idUser}")
    public Profile getUserFollowId(@PathVariable int idUser) {
        return userService.getUserFollowId(idUser);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void editUser(@RequestBody @Validated Profile profile) {
        userService.editUser(profile);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/enable")
    public void enableUser(@RequestParam int idUser) {
        userService.setStatusEnableOfUser(idUser, true);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/disable")
    public void disableUser(@RequestParam int idUser) {
        userService.setStatusEnableOfUser(idUser, false);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pagination")
    public List<Profile> getListItem(@RequestParam(name = "sizeList") int sizeList,
                                     @RequestParam(name = "indexPage") int indexPage,
                                     @RequestParam(name = "valueSearch") String valueSearch,
                                     @RequestParam(name = "keySort") String valueSort) {
        return userService.getListItem(sizeList, indexPage, valueSearch, valueSort);
    }
}
