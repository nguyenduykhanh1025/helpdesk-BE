package com.backend.helpdesk.exception.SkillsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SkillsExceptionController {

    @ExceptionHandler(value = SkillsNotFound.class)
    public ResponseEntity<Object> exception(SkillsNotFound exception) {
        return new ResponseEntity<>("Skill is not found", HttpStatus.NOT_FOUND);
    }
}
