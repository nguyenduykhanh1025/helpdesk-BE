package com.backend.helpdesk.exception.UserException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = EmailUserIsNotMatch.class)
    public ResponseEntity<Object> exception(EmailUserIsNotMatch exception) {
        return new ResponseEntity<>("Email User Is Not Match", HttpStatus.NOT_ACCEPTABLE);
    }

}
