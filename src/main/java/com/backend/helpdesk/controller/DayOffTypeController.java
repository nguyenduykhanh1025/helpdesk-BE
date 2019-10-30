package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.service.DayOffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/day_off_types")
public class DayOffTypeController {

    @Autowired
    private DayOffTypeService dayOffTypeService;

    @Secured("ROLE_EMPLOYEES")
    @GetMapping
    public List<DayOffTypeDTO> getAllDayOffType() {
        return dayOffTypeService.getAllDayOffType();
    }

    @Secured("ROLE_EMPLOYEES")
    @GetMapping("/{id}")
    public DayOffTypeDTO getDayOffTypeById(@PathVariable("id") int id) {
        return dayOffTypeService.getdayOffTypeById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public DayOffType addDayOffType(@Valid @RequestBody DayOffTypeDTO dayOffTypeDTO) {
        return dayOffTypeService.addDayOffType(dayOffTypeDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteDayOffType(@PathVariable("id") int id) {
        dayOffTypeService.deleteDayOffType(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public DayOffType deleteDayOffType(@PathVariable("id") int id, @Valid @RequestBody DayOffTypeDTO dayOffTypeDTO) {
        return dayOffTypeService.editDayOffType(id, dayOffTypeDTO);
    }

}
