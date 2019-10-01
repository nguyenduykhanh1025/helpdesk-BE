package com.backend.helpdesk.controller;

import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.DTO.DayOffDTO;
import com.backend.helpdesk.respone.NumberOfDayOff;
import com.backend.helpdesk.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
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
    @GetMapping("/the_number_of_day_off_by_user/{id}")
    public long getNumberOfDayOffByUser(@PathVariable("id") int id, @RequestParam(value = "year", required = false) int year) {
        return dayOffService.getNumberOfDayOffByUser(id, year);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("user_of_year/{id}")
    public List<DayOffDTO> getListDayOffUsed(@PathVariable("id") int id, @RequestParam(value = "year", required = false) Integer year) {
        return dayOffService.getListDayOffUsed(id, year);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/number_of_day_off/{id}")
    public NumberOfDayOff getNumberDayOffByUser(@PathVariable("id") int id, @RequestParam(value = "year", required = false) int year) {
        return dayOffService.getNumberOffDayOff(id, year);
    }

    @Secured("ROLE_EMPLOYEES")
    @PostMapping
    public DayOff addDayOff(@Valid @RequestBody DayOffDTO dayOffDTO) {
        return dayOffService.addDayOff(dayOffDTO);
    }

    @Secured("ROLE_EMPLOYEES")
    @DeleteMapping("/{id}")
    public void deleteDayOff(@PathVariable("id") int id) {
        dayOffService.deleteDayOff(id);
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/pagination")
    public List<DayOffDTO> pagination(@RequestParam(value = "sizeList", required = false) int sizeList,
                                      @RequestParam(value = "indexPage", required = false) int indexPage,
                                      @RequestParam(value = "content", required = false) String content) {
        return dayOffService.pagination(sizeList, indexPage, content);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("accept/{id}")
    public DayOff acceptDayOff(@PathVariable("id") int id){
        return dayOffService.acceptDayOff(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("rejected/{id}")
    public DayOff rejectedDayOff(@PathVariable("id") int id){
        return dayOffService.rejectedDayOff(id);
    }
}
