package com.backend.helpdesk.exception.FileException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class FileExceptionController {
    @ExceptionHandler(value = FileNotFound.class)
    public ResponseEntity<Object> exception(FileNotFound exception) {
        return new ResponseEntity<>("File is not found", HttpStatus.NOT_FOUND);
    }
}
