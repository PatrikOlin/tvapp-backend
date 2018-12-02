package com.tvapp.utils.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidUserAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String invalidUserHandler(InvalidUserException ex) {
        return ex.getMessage();
    }
}
