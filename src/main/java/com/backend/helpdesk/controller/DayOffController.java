package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entityDTO.DayOffDTO;
import com.backend.helpdesk.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/day_offs")
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping
    public List<DayOff> getAllDayOffs() {
        return dayOffService.getAllDayOff();
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("enable")
    public List<DayOff> getDayOffByEnable(@RequestParam(value = "enable", required = false) String enable) {
        return dayOffService.getDayOffsByStatus(enable);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/user/{id}")
    public List<DayOff> getDayOffByUser(@PathVariable("id") int id) {
        return dayOffService.getDayOffByUser(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/the_number_of_day_off_by_user/{id}")
    public long getNumberOfDayOffByUser(@PathVariable("id") int id,@RequestParam(value = "year", required = false) int year) {
        return dayOffService.getNumberOfDayOffByUser(id,year);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("user_of_year/{id}")
    public List<DayOffDTO> getListDayOffUsed(@PathVariable("id") int id, @RequestParam(value = "year", required = false) int year) {
        return dayOffService.getListDayOffUsed(id,year);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/the_number_of_day_off_by_user_used/{id}")
    public float getNumberOfDayOffUsed(@PathVariable("id") int id, @RequestParam(value = "year", required = false) int year) {
        return dayOffService.getNumberOfDayOffUsed(id,year);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/the_number_of_day_off_remaining_user/{id}")
    public float getNumberDayOffByUserRemaining(@PathVariable("id") int id, @RequestParam(value = "year", required = false) int year) {
        return dayOffService.getNumberDayOffByUserRemaining(id,year);
    }

    @Secured("ROLE_EMPLOYEES")
    @PostMapping("/create")
    public DayOff addDayOff(@Valid @RequestBody DayOffDTO dayOffDTO){
        return dayOffService.addDayOff(dayOffDTO);
    }

    @Secured("ROLE_EMPLOYEES")
    @DeleteMapping("/{id}")
    public void deleteDayOff(@PathVariable("id") int id){
        dayOffService.deleteDayOff(id);
    }

}
