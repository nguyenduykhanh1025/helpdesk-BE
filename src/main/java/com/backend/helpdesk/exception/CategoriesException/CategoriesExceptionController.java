package com.backend.helpdesk.exception.CategoriesException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoriesExceptionController {
    @ExceptionHandler(value = CategoriesNotFound.class)
    public ResponseEntity<Object> exception(CategoriesNotFound exception) {
        return new ResponseEntity<>("Categories not found", HttpStatus.NOT_FOUND);
    }
}
